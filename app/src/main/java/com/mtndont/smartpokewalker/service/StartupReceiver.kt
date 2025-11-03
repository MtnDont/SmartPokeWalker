package com.mtndont.smartpokewalker.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

/**
 * Background data subscriptions are not persisted across device restarts. This receiver checks if
 * we enabled background data and, if so, registers again.
 */
class StartupReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        // Make sure we have permission.
        val result = context.checkSelfPermission(android.Manifest.permission.BODY_SENSORS)
        if (result == PackageManager.PERMISSION_GRANTED) {
            context.startForegroundService(
                Intent(
                    context.applicationContext,
                    StepService::class.java
                )
            )
        }
    }
}