package com.mtndont.smartpokewalker.presentation

import android.Manifest
import android.content.Intent
import android.health.connect.HealthPermissions
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

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.ACTIVITY_RECOGNITION,
                Manifest.permission.BODY_SENSORS,
                HealthPermissions.READ_STEPS,
                Manifest.permission.BODY_SENSORS_BACKGROUND,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_SCAN
            )
        )

        ContextCompat.startForegroundService(
            this.applicationContext,
            Intent(
                this.applicationContext,
                StepService::class.java
            )
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
        }
}