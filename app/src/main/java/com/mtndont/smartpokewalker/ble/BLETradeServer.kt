package com.mtndont.smartpokewalker.ble

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.os.ParcelUuid
import androidx.annotation.RequiresPermission
import com.mtndont.smartpokewalker.data.MonsterModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class BLETradeServer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val bluetoothManager: BluetoothManager
) {
    private var gattServer: BluetoothGattServer? = null
    private var connectedDevice: BluetoothDevice? = null
    private val confirmation = MutualConfirmation()
    private var pendingOffer: MonsterModel? = null
    private lateinit var myMonster: MonsterModel

    private lateinit var tradeCode: ByteArray

    private var advertiseCallback: AdvertiseCallback? = null

    private var onOfferReceived: ((MonsterModel) -> Unit)? = null
    private var onTradeComplete: ((MonsterModel) -> Unit)? = null
    private var onTradeCancelled: (() -> Unit)? = null

    fun setCallbacks(
        onOfferReceived: (MonsterModel) -> Unit,
        onTradeComplete: (MonsterModel) -> Unit,
        onTradeCancelled: () -> Unit
    ) {
        this.onOfferReceived = onOfferReceived
        this.onTradeComplete = onTradeComplete
        this.onTradeCancelled = onTradeCancelled
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun startServer(myMonster: MonsterModel): String? {
        this.myMonster = myMonster
        confirmation.localState = TradeConfirmState.NONE
        confirmation.remoteState = TradeConfirmState.NONE

        gattServer = bluetoothManager.openGattServer(context, gattCallback)
            ?: run {
                onTradeCancelled?.invoke()
                return null
            }

        gattServer?.addService(buildService())
        startAdvertising()

        val code = (tradeCode[0].toUByte().toInt() shl 8) or (tradeCode[1].toUByte().toInt())
        return code.toString().padStart(5, '0')
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun submitLocalDecision(confirmed: Boolean) {
        confirmation.localState = if (confirmed)
            TradeConfirmState.CONFIRMED else TradeConfirmState.CANCELLED

        val confirmChar = gattServer
            ?.getService(BleTradeUUIDs.SERVICE_UUID)
            ?.getCharacteristic(BleTradeUUIDs.TRADE_CONFIRM_UUID) ?: return

        connectedDevice?.let { device ->
            gattServer?.notifyCharacteristicChangedCompat(
                device,
                confirmChar,
                false,
                byteArrayOf(if (confirmed) 0x01 else 0x00)
            )
        }

        checkMutualConfirmation()
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT
    ])
    fun stop() {
        stopAdvertising()
        gattServer?.close()
        gattServer = null
        connectedDevice = null
    }

    private fun buildService(): BluetoothGattService {
        val service = BluetoothGattService(
            BleTradeUUIDs.SERVICE_UUID,
            BluetoothGattService.SERVICE_TYPE_PRIMARY
        )

        // MONSTER_OFFER_UUID — client writes its Monster; server notifies its Monster back
        val offerChar = BluetoothGattCharacteristic(
            BleTradeUUIDs.MONSTER_OFFER_UUID,
            BluetoothGattCharacteristic.PROPERTY_WRITE or
                    BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_WRITE
        ).also { char ->
            char.addDescriptor(buildCccd())
        }

        // TRADE_CONFIRM_UUID — client writes its decision; server notifies its decision back
        val confirmChar = BluetoothGattCharacteristic(
            BleTradeUUIDs.TRADE_CONFIRM_UUID,
            BluetoothGattCharacteristic.PROPERTY_WRITE or
                    BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_WRITE
        ).also { char ->
            char.addDescriptor(buildCccd())
        }

        service.addCharacteristic(offerChar)
        service.addCharacteristic(confirmChar)
        return service
    }

    private fun buildCccd() = BluetoothGattDescriptor(
        BleTradeUUIDs.CCCD_UUID,
        BluetoothGattDescriptor.PERMISSION_READ or BluetoothGattDescriptor.PERMISSION_WRITE
    )

    private fun startAdvertising() {
        val advertiser = bluetoothManager.adapter.bluetoothLeAdvertiser ?: return

        val pairingCode = Random.nextBytes(ByteArray(2))
        tradeCode = pairingCode

        val settings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
            .setConnectable(true)
            .build()

        val data = AdvertiseData.Builder()
            .addServiceUuid(ParcelUuid(BleTradeUUIDs.SERVICE_UUID))
            .addServiceData(
                ParcelUuid(BleTradeUUIDs.SERVICE_UUID),
                pairingCode
            )
            .setIncludeDeviceName(false)
            .build()

        advertiseCallback = object: AdvertiseCallback() {
            override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) { /* advertising */ }
            override fun onStartFailure(errorCode: Int) { onTradeCancelled?.invoke() }
        }

        advertiser.startAdvertising(settings, data, advertiseCallback)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
    private fun stopAdvertising() {
        advertiseCallback?.let {
            bluetoothManager.adapter.bluetoothLeAdvertiser?.stopAdvertising(it)
        }
        advertiseCallback = null
    }

    private val gattCallback = object: BluetoothGattServerCallback() {

        @RequiresPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
        override fun onConnectionStateChange(
            device: BluetoothDevice,
            status: Int,
            newState: Int
        ) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    // Stop advertising once a client connects so no second
                    // device can interrupt an in-progress trade
                    stopAdvertising()
                    connectedDevice = device
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    if (!confirmation.bothConfirmed) {
                        onTradeCancelled?.invoke()
                    }
                    connectedDevice = null
                }
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onCharacteristicWriteRequest(
            device: BluetoothDevice,
            requestId: Int,
            characteristic: BluetoothGattCharacteristic,
            preparedWrite: Boolean,
            responseNeeded: Boolean,
            offset: Int,
            value: ByteArray
        ) {
            if (responseNeeded) {
                gattServer?.sendResponse(device, requestId, GATT_SUCCESS, 0, null)
            }

            when (characteristic.uuid) {
                BleTradeUUIDs.MONSTER_OFFER_UUID -> handleOfferReceived(device, value)
                BleTradeUUIDs.TRADE_CONFIRM_UUID -> handleConfirmReceived(value)
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onDescriptorWriteRequest(
            device: BluetoothDevice,
            requestId: Int,
            descriptor: BluetoothGattDescriptor,
            preparedWrite: Boolean,
            responseNeeded: Boolean,
            offset: Int,
            value: ByteArray
        ) {
            // Store the subscription state so notifyCharacteristicChanged
            // knows which clients have notifications enabled
            descriptor.value = value
            if (responseNeeded) {
                gattServer?.sendResponse(device, requestId, GATT_SUCCESS, 0, null)
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onDescriptorReadRequest(
            device: BluetoothDevice,
            requestId: Int,
            offset: Int,
            descriptor: BluetoothGattDescriptor
        ) {
            gattServer?.sendResponse(
                device,
                requestId,
                GATT_SUCCESS,
                0,
                descriptor.value
            )
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun handleOfferReceived(device: BluetoothDevice, value: ByteArray) {
        val parsed = MonsterModel.fromBytes(value)
        if (parsed == null) {
            onTradeCancelled?.invoke()
            return
        }
        pendingOffer = parsed
        onOfferReceived?.invoke(parsed)

        val offerChar = gattServer
            ?.getService(BleTradeUUIDs.SERVICE_UUID)
            ?.getCharacteristic(BleTradeUUIDs.MONSTER_OFFER_UUID) ?: return

        gattServer?.notifyCharacteristicChangedCompat(
            device,
            offerChar,
            false,
            myMonster.toBytes()
        )
    }

    private fun handleConfirmReceived(value: ByteArray) {
        confirmation.remoteState = when (value.firstOrNull()) {
            0x01.toByte() -> TradeConfirmState.CONFIRMED
            else          -> TradeConfirmState.CANCELLED
        }
        checkMutualConfirmation()
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
}