package com.mtndont.smartpokewalker.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import com.mtndont.smartpokewalker.R
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.data.MonstersRepository
import com.mtndont.smartpokewalker.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StepService : Service(), SensorEventListener {

    private val totalStepScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val detectScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var stepsOnOpen = -1L
    private var lastSteps = -1L

    @Inject
    lateinit var monsterDataRepository: MonsterDataRepository

    @Inject
    lateinit var monstersRepository: MonstersRepository

    private val sensorManager by lazy {
        this.applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager
    }
    private val totalStepsSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(1, getNotification())
        sensorManager.unregisterListener(this)

        totalStepsSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    private fun getNotification(): Notification {
        val channel = NotificationChannel(
            "step_channel",
            "SmartPokewalker Service",
            NotificationManager.IMPORTANCE_LOW
        )
        val notifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notifManager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, "step_channel")
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
        val notifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notifManager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, EXPLORE_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.explore_refreshed))
            .setContentText(getString(R.string.explore_flavor_text))
            .setContentIntent(
                PendingIntent.getActivity(
                    this.applicationContext,
                    4707,
                    Intent(this.applicationContext, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()
    }

    private fun showExploreNotification() {
        if (ContextCompat.checkSelfPermission(
                this@StepService,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED) {
            with(NotificationManagerCompat.from(this@StepService)) {
                notify(
                    EXPLORE_NOTIFICATION_ID,
                    getExploreNotification()
                )
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (totalStepsSensor != null) {
            sensorManager.unregisterListener(this)
        }
        totalStepScope.cancel()
        detectScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        when (sensor?.type) {
            Sensor.TYPE_STEP_DETECTOR, Sensor.TYPE_STEP_COUNTER -> {
                Log.d("SensorRepository", "Accuracy changed to: $accuracy")
            }
            else -> return
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_STEP_DETECTOR -> {
                /*detectScope.launch {
                    //Log.d("StepService", "Type: ${event.sensor.stringType}\nValue: ${event.values[0].toLong()}\naccuracy: ${event.accuracy}")
                    //monsterDataRepository.addCurrentSteps(1L)
                    monstersRepository.addStepsToParty(1L)
                }*/
            }
            Sensor.TYPE_STEP_COUNTER -> {
                if (stepsOnOpen == -1L) {
                    stepsOnOpen = event.values[0].toLong()
                    lastSteps = event.values[0].toLong()
                }

                val stepsSinceBoot = event.values[0].toLong()
                val delta = stepsSinceBoot - lastSteps
                lastSteps = stepsSinceBoot

                //Log.d("StepService", "Type: ${event.sensor.stringType}\n Steps: $stepsSinceBoot")
                totalStepScope.launch {
                    monsterDataRepository.setTotalWatts(stepsSinceBoot)
                    if (delta > 0) {
                        // STEP_COUNTER appears to be more trustworthy over the STEP_DETECTOR
                        //monsterDataRepository.addCurrentSteps(delta)
                        val exploreSteps = monsterDataRepository.exploreSteps.first()
                        monsterDataRepository.addExploreSteps(delta)

                        if (exploreSteps < MonsterModel.MAX_EXPLORE_STEPS && (exploreSteps+delta) >= MonsterModel.MAX_EXPLORE_STEPS) {
                            showExploreNotification()
                        }

                        monstersRepository.addStepsToParty(delta)
                    }
                }
            }
            else -> return
        }
    }

    companion object {
        const val EXPLORE_NOTIFICATION_CHANNEL_ID = "explore_notification"
        const val EXPLORE_NOTIFICATION_ID = 4073
    }
}