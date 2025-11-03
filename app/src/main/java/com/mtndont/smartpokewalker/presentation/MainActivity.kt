/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.mtndont.smartpokewalker.presentation

import android.Manifest
import android.content.Intent
import android.health.connect.HealthPermissions
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mtndont.smartpokewalker.service.StepService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                        Manifest.permission.ACTIVITY_RECOGNITION,
                    Manifest.permission.BODY_SENSORS
                ),
                0
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(HealthPermissions.READ_STEPS),
                0
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BODY_SENSORS_BACKGROUND),
                0
            )
        }

        ContextCompat.startForegroundService(
            this.applicationContext,
            Intent(
                this.applicationContext,
                StepService::class.java
            )
        )

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WalkPagerApp()
        }
    }
}