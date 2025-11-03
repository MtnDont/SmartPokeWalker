package com.mtndont.smartpokewalker.di

import android.content.Context
import androidx.room.Room
import com.mtndont.smartpokewalker.data.AppDatabase
import com.mtndont.smartpokewalker.data.DefaultMonstersRepository
import com.mtndont.smartpokewalker.data.MonstersDao
import com.mtndont.smartpokewalker.data.MonstersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMonsterRepository(repository: DefaultMonstersRepository): MonstersRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideMonstersDao(database: AppDatabase): MonstersDao = database.monstersDao()
}