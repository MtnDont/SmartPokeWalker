package com.mtndont.smartpokewalker.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import com.mtndont.smartpokewalker.data.MonstersRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
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
            "Smart Pokewalker Service",
            NotificationManager.IMPORTANCE_LOW
        )
        val notifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notifManager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, "step_channel")
            .setContentTitle("Smart Pokewalker Running")
            .setContentText("counting...")
            //.setSmallIcon(R.raw.dittopng)
            .build()
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
                detectScope.launch {
                    //Log.d("StepService", "Type: ${event.sensor.stringType}\nValue: ${event.values[0].toLong()}\naccuracy: ${event.accuracy}")
                    //monsterDataRepository.addCurrentSteps(1L)
                    monstersRepository.addStepsToParty(1L)
                }
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
                        monstersRepository.addStepsToParty(delta)
                    }
                }
            }
            else -> return
        }
    }
}