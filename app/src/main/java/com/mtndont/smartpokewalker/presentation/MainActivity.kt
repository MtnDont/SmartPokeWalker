package com.mtndont.smartpokewalker.presentation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mtndont.smartpokewalker.service.StepService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val criticalPermissions = arrayOf(
        Manifest.permission.ACTIVITY_RECOGNITION
    )

    private val permissions = arrayOf(
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.POST_NOTIFICATIONS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestMultiplePermissions.launch(
            criticalPermissions.plus(permissions)
        )

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            TrainerNav()
        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d("MainActivity", "${it.key} = ${it.value}")
            }

            val criticalGranted = criticalPermissions.all {
                permissions[it] ?: false
            }

            if (criticalGranted) {
                ContextCompat.startForegroundService(
                    this.applicationContext,
                    Intent(
                        this.applicationContext,
                        StepService::class.java
                    )
                )
            } else {
                Log.e("MainActivity", "Critical permissions denied. Unable to start StepService")
            }
        }
}