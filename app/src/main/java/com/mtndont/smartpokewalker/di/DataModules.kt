package com.mtndont.smartpokewalker.di

import android.content.Context
import androidx.room.Room
import com.mtndont.smartpokewalker.data.AppDatabase
import com.mtndont.smartpokewalker.data.MonstersDao
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class DataModule {

    @Single
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Factory
    fun provideMonstersDao(database: AppDatabase): MonstersDao = database.monstersDao()
}