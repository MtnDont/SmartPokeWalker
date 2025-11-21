package com.mtndont.smartpokewalker.di

import android.bluetooth.BluetoothManager
import android.content.Context
import com.mtndont.smartpokewalker.ble.BLEManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BleModule {

    @Provides
    @Singleton
    fun provideBluetoothManager(
        @ApplicationContext context: Context
    ): BluetoothManager = context.getSystemService(BluetoothManager::class.java)

    @Provides
    @Singleton
    fun provideBLEManager(
        @ApplicationContext context: Context,
        bluetoothManager: BluetoothManager
    ): BLEManager = BLEManager(context, bluetoothManager)
}