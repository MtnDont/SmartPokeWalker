package com.mtndont.smartpokewalker.ble

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.ParcelUuid
import androidx.annotation.RequiresPermission
import com.mtndont.smartpokewalker.data.MonsterModel
import java.util.UUID

class BLETradeClient(
    private val context: Context,
    private val bluetoothManager: BluetoothManager
) {
    private var gatt: BluetoothGatt? = null
    private val confirmation = MutualConfirmation()
    private var pendingOffer: MonsterModel? = null
    private lateinit var myMonster: MonsterModel

    private var scanCallback: ScanCallback? = null
    private val discoveredHosts = mutableMapOf<String, DiscoveredHost>()
    private var scanActive = false

    private var onHostsUpdated: ((List<DiscoveredHost>) -> Unit)? = null
    private var onOfferReceived: ((MonsterModel) -> Unit)? = null
    private var onTradeComplete: ((MonsterModel) -> Unit)? = null
    private var onTradeCancelled: (() -> Unit)? = null

    fun setCallbacks(
        onHostsUpdated: (List<DiscoveredHost>) -> Unit,
        onOfferReceived: (MonsterModel) -> Unit,
        onTradeComplete: (MonsterModel) -> Unit,
        onTradeCancelled: () -> Unit
    ) {
        this.onHostsUpdated = onHostsUpdated
        this.onOfferReceived = onOfferReceived
        this.onTradeComplete = onTradeComplete
        this.onTradeCancelled = onTradeCancelled
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun scanHosts(myMonster: MonsterModel) {
        // Stop scans before starting a new one
        stopScan()
        scanActive = true

        this.myMonster = myMonster
        confirmation.localState = TradeConfirmState.NONE
        confirmation.remoteState = TradeConfirmState.NONE
        discoveredHosts.clear()

        val adapter = bluetoothManager.adapter
        val scanner = adapter.bluetoothLeScanner ?: return

        val filter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid(BleTradeUUIDs.SERVICE_UUID))
            .build()

        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        scanCallback = object: ScanCallback() {
            @RequiresPermission(allOf = [
                Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN
            ])
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                //stopScan()
                //connect(result.device)
                if (!scanActive) return

                val code = parseTradeCode(result) ?: return
                val existing = discoveredHosts[result.device.address]
                if (existing != null) {
                    existing.addRssiSample(result.rssi)
                } else {
                    val host = DiscoveredHost(
                        result.device,
                        code
                    )
                    host.addRssiSample(result.rssi)
                    discoveredHosts[result.device.address] = host
                }
                // Sort closest first so the two people trading are likely at the top
                onHostsUpdated?.invoke(
                    discoveredHosts.values.sortedByDescending { it.rssi }
                )
            }

            override fun onScanFailed(errorCode: Int) {
                onTradeCancelled?.invoke()
            }
        }

        scanner.startScan(listOf(filter), settings, scanCallback)
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN
    ])
    fun connectToHost(host: DiscoveredHost): Boolean {
        stopScan()

        if (host !in discoveredHosts.values || host.device == null) {
            return false
        }

        connect(host.device)
        return true
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun submitLocalDecision(confirmed: Boolean) {
        confirmation.localState = if (confirmed)
            TradeConfirmState.CONFIRMED else TradeConfirmState.CANCELLED

        val confirmChar = gatt
            ?.getService(BleTradeUUIDs.SERVICE_UUID)
            ?.getCharacteristic(BleTradeUUIDs.TRADE_CONFIRM_UUID) ?: return

        gatt?.writeCharacteristicCompat(
            confirmChar,
            byteArrayOf(if (confirmed) 0x01 else 0x00)
        )

        checkMutualConfirmation()
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN
    ])
    fun disconnect() {
        stopScan()
        gatt?.close()
        gatt = null
        discoveredHosts.clear()
    }


    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun connect(device: BluetoothDevice) {
        gatt = device.connectGatt(context, false, gattCallback, BluetoothDevice.TRANSPORT_LE)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    private fun stopScan() {
        scanActive = false
        val adapter = bluetoothManager.adapter
        scanCallback?.let { adapter.bluetoothLeScanner?.stopScan(it) }
        scanCallback = null
    }

    private val gattCallback = object: BluetoothGattCallback() {

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onConnectionStateChange(g: BluetoothGatt, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> g.discoverServices()
                BluetoothProfile.STATE_DISCONNECTED -> {
                    if (!confirmation.bothConfirmed) {
                        onTradeCancelled?.invoke()
                    }
                }
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onServicesDiscovered(g: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                g.requestMtu(185)
            } else {
                onTradeCancelled?.invoke()
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onMtuChanged(g: BluetoothGatt, mtu: Int, status: Int) {
            // Proceed regardless of whether the full MTU was granted
            // the server may grant less than requested, but anything
            // at least 32 bytes is enough for our payload
            subscribeAndOffer(g)
        }

        private val subscribeQueue = ArrayDeque<UUID>()
        private var offerSent = false

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onDescriptorWrite(
            g: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int
        ) {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                onTradeCancelled?.invoke()
                return
            }

            val nextUuid = subscribeQueue.removeFirstOrNull()
            if (nextUuid != null) {
                subscribeToCharacteristic(g, nextUuid)
            } else if (!offerSent) {
                offerSent = true
                sendMonsterOffer(g)
            }
        }

        override fun onCharacteristicWrite(
            g: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                onTradeCancelled?.invoke()
            }
        }

        @Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")
        override fun onCharacteristicChanged(
            g: BluetoothGatt,
            char: BluetoothGattCharacteristic
        ) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                handleCharacteristicChanged(char.uuid, char.value)
            }
        }

        override fun onCharacteristicChanged(
            g: BluetoothGatt,
            char: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            handleCharacteristicChanged(char.uuid, value)
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        private fun subscribeAndOffer(g: BluetoothGatt) {
            subscribeQueue.clear()
            offerSent = false
            subscribeQueue.add(BleTradeUUIDs.TRADE_CONFIRM_UUID)
            subscribeToCharacteristic(g, BleTradeUUIDs.MONSTER_OFFER_UUID)
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        private fun subscribeToCharacteristic(g: BluetoothGatt, uuid: UUID) {
            val service = g.getService(BleTradeUUIDs.SERVICE_UUID) ?: return
            val char = service.getCharacteristic(uuid) ?: return
            g.setCharacteristicNotification(char, true)
            val cccd = char.getDescriptor(BleTradeUUIDs.CCCD_UUID) ?: return
            g.writeDescriptorCompat(cccd, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        private fun sendMonsterOffer(g: BluetoothGatt) {
            val service = g.getService(BleTradeUUIDs.SERVICE_UUID) ?: return
            val offerChar = service.getCharacteristic(BleTradeUUIDs.MONSTER_OFFER_UUID) ?: return
            g.writeCharacteristicCompat(offerChar, myMonster.toBytes())
        }
    }

    private fun handleCharacteristicChanged(uuid: UUID, value: ByteArray) {
        when (uuid) {
            BleTradeUUIDs.MONSTER_OFFER_UUID -> {
                val parsed = MonsterModel.fromBytes(value)
                if (parsed == null) {
                    onTradeCancelled?.invoke()
                    return
                }
                pendingOffer = parsed
                onOfferReceived?.invoke(parsed)
            }
            BleTradeUUIDs.TRADE_CONFIRM_UUID -> {
                confirmation.remoteState = when (value.firstOrNull()) {
                    0x01.toByte() -> TradeConfirmState.CONFIRMED
                    else          -> TradeConfirmState.CANCELLED
                }
                checkMutualConfirmation()
            }
        }
    }

    private fun checkMutualConfirmation() {
        when {
            confirmation.bothConfirmed -> {
                val offer = pendingOffer
                if (offer == null) {
                    onTradeCancelled?.invoke()
                } else {
                    onTradeComplete?.invoke(offer)
                }
            }
            confirmation.eitherCancelled -> onTradeCancelled?.invoke()
        }
    }


    private fun parseTradeCode(result: ScanResult): String? {
        val bytes = result.scanRecord
            ?.getServiceData(ParcelUuid(BleTradeUUIDs.SERVICE_UUID))
            ?: return null
        if (bytes.size < 2) return null
        val codeInt = ((bytes[0].toUByte().toInt()) shl 8) or (bytes[1].toUByte().toInt())
        return codeInt.toString().padStart(5, '0')
    }
}

object BleTradeUUIDs {
    val SERVICE_UUID: UUID = UUID.fromString("12345678-0000-1000-8000-00805f9b34fb")
    val MONSTER_OFFER_UUID: UUID = UUID.fromString("12345678-0001-1000-8000-00805f9b34fb")
    val TRADE_CONFIRM_UUID: UUID = UUID.fromString("12345678-0002-1000-8000-00805f9b34fb")
    val CCCD_UUID: UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
}

class DiscoveredHost(
    val device: BluetoothDevice?,
    // 6-digit code displayed on the host's watch
    val code: String,
) {
    private val rssiSamples = ArrayDeque<Int>(10)

    // signal strength
    val rssi: Int get() = if (rssiSamples.isEmpty()) Int.MIN_VALUE
        else rssiSamples.average().toInt()

    fun addRssiSample(rssi: Int) {
        if (rssiSamples.size >= 10) rssiSamples.removeFirst()
        rssiSamples.addLast(rssi)
    }
}

enum class TradeConfirmState { NONE, CONFIRMED, CANCELLED }

data class MutualConfirmation(
    var localState: TradeConfirmState = TradeConfirmState.NONE,
    var remoteState: TradeConfirmState = TradeConfirmState.NONE,
) {
    val bothConfirmed get() =
        localState == TradeConfirmState.CONFIRMED &&
                remoteState == TradeConfirmState.CONFIRMED

    val eitherCancelled get() =
        localState == TradeConfirmState.CANCELLED ||
                remoteState == TradeConfirmState.CANCELLED
}