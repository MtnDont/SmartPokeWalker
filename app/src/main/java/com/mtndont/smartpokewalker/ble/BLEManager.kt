package com.mtndont.smartpokewalker.ble

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BLEManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val bluetoothManager: BluetoothManager
) {
    private val adapter = bluetoothManager.adapter
    private val advertiser = adapter.bluetoothLeAdvertiser
    private val scanner = adapter.bluetoothLeScanner

    private var gattServer: BluetoothGattServer? = null

    private val _incomingMessages = MutableSharedFlow<String>()
    val incomingMessages = _incomingMessages.asSharedFlow()

    private val messageUUID = UUID.fromString("0000aaaa-0000-1000-8000-00805f9b34fb")
    private val serviceUUID = UUID.fromString("0000bbbb-0000-1000-8000-00805f9b34fb")

    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            Log.d("BLE", "Advertising started")
        }

        override fun onStartFailure(errorCode: Int) {
            Log.e("BLE", "Advertising failed: $errorCode")
            ADVERTISE_FAILED_ALREADY_STARTED
        }
    }

    /** Start BLE GATT Server */
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun startServer() {
        gattServer = bluetoothManager.openGattServer(context, object : BluetoothGattServerCallback() {
            @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
            override fun onCharacteristicWriteRequest(
                device: BluetoothDevice?,
                requestId: Int,
                characteristic: BluetoothGattCharacteristic?,
                preparedWrite: Boolean,
                responseNeeded: Boolean,
                offset: Int,
                value: ByteArray?
            ) {
                value?.let {
                    _incomingMessages.tryEmit(String(it))
                }
                gattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, null)
            }
        })

        val service = BluetoothGattService(serviceUUID, BluetoothGattService.SERVICE_TYPE_PRIMARY)
        val characteristic = BluetoothGattCharacteristic(
            messageUUID,
            BluetoothGattCharacteristic.PROPERTY_WRITE,
            BluetoothGattCharacteristic.PERMISSION_WRITE
        )

        service.addCharacteristic(characteristic)
        gattServer?.addService(service)

        advertiser.startAdvertising(
            AdvertiseSettings.Builder().setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY).build(),
            AdvertiseData.Builder()
                .setIncludeDeviceName(false)
                .addServiceUuid(ParcelUuid(serviceUUID))
                .addServiceData(
                    ParcelUuid(serviceUUID),
                    "pokewalker".toByteArray(Charsets.UTF_8)
                )
                .build(),
            advertiseCallback
        )
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
    fun stopServer() {
        advertiser.stopAdvertising(advertiseCallback)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            gattServer?.close()
        }

        gattServer = null
    }

    /** Scan for other devices and connect as GATT client */
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan(onDeviceFound: (BluetoothDevice) -> Unit) {
        scanner.startScan(object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                result?.device?.let(onDeviceFound)
            }
        })
    }

    /** Send data to another watch */
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun sendMessage(device: BluetoothDevice, msg: String) {
        device.connectGatt(context, false, object : BluetoothGattCallback() {
            @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
            @Suppress("DEPRECATION")
            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                val service = gatt?.getService(serviceUUID)

                service?.getCharacteristic(messageUUID)?.let { characteristic ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        gatt.writeCharacteristic(
                            characteristic,
                            msg.toByteArray(),
                            characteristic.writeType
                        )
                    }
                    else {
                        characteristic.value = msg.toByteArray()
                        gatt.writeCharacteristic(characteristic)
                    }
                }
            }
        })
    }
}