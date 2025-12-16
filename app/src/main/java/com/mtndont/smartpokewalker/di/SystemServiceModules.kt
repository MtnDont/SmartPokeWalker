package com.mtndont.smartpokewalker.di

import android.app.NotificationManager
import android.content.Context
import com.mtndont.smartpokewalker.util.NotificationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideNotificationUtil(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager
    ): NotificationUtil = NotificationUtil(context, notificationManager)
}