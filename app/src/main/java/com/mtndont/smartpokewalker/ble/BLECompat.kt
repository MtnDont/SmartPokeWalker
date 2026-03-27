package com.mtndont.smartpokewalker.ble

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothStatusCodes
import android.os.Build
import androidx.annotation.RequiresPermission

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun BluetoothGatt.writeCharacteristicCompat(
    characteristic: BluetoothGattCharacteristic,
    value: ByteArray,
    writeType: Int = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        writeCharacteristic(characteristic, value, writeType) ==
                BluetoothStatusCodes.SUCCESS
    } else {
        @Suppress("DEPRECATION")
        characteristic.value = value
        characteristic.writeType = writeType
        @Suppress("DEPRECATION")
        writeCharacteristic(characteristic)
    }
}

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun BluetoothGatt.writeDescriptorCompat(
    descriptor: BluetoothGattDescriptor,
    value: ByteArray
): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        writeDescriptor(descriptor, value) == BluetoothStatusCodes.SUCCESS
    } else {
        @Suppress("DEPRECATION")
        descriptor.value = value
        @Suppress("DEPRECATION")
        writeDescriptor(descriptor)
    }
}

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun BluetoothGattServer.notifyCharacteristicChangedCompat(
    device: BluetoothDevice,
    characteristic: BluetoothGattCharacteristic,
    confirm: Boolean,
    value: ByteArray
): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        notifyCharacteristicChanged(device, characteristic, confirm, value) ==
                BluetoothStatusCodes.SUCCESS
    } else {
        @Suppress("DEPRECATION")
        characteristic.value = value
        @Suppress("DEPRECATION")
        notifyCharacteristicChanged(device, characteristic, confirm)
        true
    }
}