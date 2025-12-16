package com.mtndont.smartpokewalker.util

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.presentation.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationUtil @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {
    fun getServiceNotification(): Notification {
        val channel = NotificationChannel(
            SERVICE_NOTIFICATION_CHANNEL_ID,
            "SmartPokewalker Service",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)

        return NotificationCompat.Builder(context, SERVICE_NOTIFICATION_CHANNEL_ID)
            .setContentTitle("SmartPokewalker Running")
            .setContentText("couting...")
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .build()
    }
    private fun getExploreNotification(): Notification {
        val channel = NotificationChannel(
            EXPLORE_NOTIFICATION_CHANNEL_ID,
            "SmartPokewalker Explore",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.enableVibration(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(channel)

        return NotificationCompat.Builder(context, EXPLORE_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.explore_refreshed))
            .setContentText(context.getString(R.string.explore_flavor_text))
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    4707,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()
    }

    fun showExploreNotification() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED) {
            with(NotificationManagerCompat.from(context)) {
                notify(
                    EXPLORE_NOTIFICATION_ID,
                    getExploreNotification()
                )
            }
        }
    }

    fun cancelNotifications() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED) {
            with(NotificationManagerCompat.from(context)) {
                cancelAll()
            }
        }
    }

    companion object {
        const val SERVICE_NOTIFICATION_CHANNEL_ID = "step_service"
        const val EXPLORE_NOTIFICATION_CHANNEL_ID = "explore_notification"
        const val EXPLORE_NOTIFICATION_ID = 4073
    }
}