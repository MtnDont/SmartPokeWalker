package com.mtndont.smartpokewalker.di

import android.app.NotificationManager
import android.content.Context
import com.mtndont.smartpokewalker.util.NotificationUtil
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class NotificationModule {
    @Single
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Single
    fun provideNotificationUtil(
        context: Context,
        notificationManager: NotificationManager
    ): NotificationUtil = NotificationUtil(context, notificationManager)
}