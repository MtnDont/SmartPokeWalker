package com.mtndont.smartpokewalker.di

import android.bluetooth.BluetoothManager
import android.content.Context
import com.mtndont.smartpokewalker.ble.BLEManager
import com.mtndont.smartpokewalker.ble.BLETradeClient
import com.mtndont.smartpokewalker.ble.BLETradeServer
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class BleModule {

    @Single
    fun provideBluetoothManager(
        context: Context
    ): BluetoothManager = context.getSystemService(BluetoothManager::class.java)

    @Single
    fun provideBLEManager(
        context: Context,
        bluetoothManager: BluetoothManager
    ): BLEManager = BLEManager(context, bluetoothManager)

    @Single
    fun provideBLETradeClient(
        context: Context,
        bluetoothManager: BluetoothManager
    ): BLETradeClient = BLETradeClient(context, bluetoothManager)

    @Single
    fun provideBLETradeServer(
        context: Context,
        bluetoothManager: BluetoothManager
    ): BLETradeServer = BLETradeServer(context, bluetoothManager)
}