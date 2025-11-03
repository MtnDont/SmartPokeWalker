package com.mtndont.smartpokewalker.data

import android.content.Context
import android.hardware.SensorManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

class SensorRepository(context: Context) {
    private val sensorManager by lazy {
        context.applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val totalStepsSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }
    private val detectStepsSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
    }

    /*fun getTotalStepsFlow(): Flow<Long> = callbackFlow {
        val listener: SensorEventListener by lazy {
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return

                    val stepsSinceLastReboot = event.values[0].toLong()
                    trySend(stepsSinceLastReboot)
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    Log.d("SensorRepository", "Accuracy changed to: $accuracy")
                }
            }
        }

        val supportedAndEnabled = sensorManager.registerListener(listener,
            totalStepsSensor, SensorManager.SENSOR_DELAY_UI)
        Log.d("SensorRepository", "Total Steps Sensor listener registered: $supportedAndEnabled")

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }.conflate()

    fun getDetectStepsFlow(): Flow<Float> = callbackFlow {
        val listener: SensorEventListener by lazy {
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return

                    val detectedSteps = event.values[0]
                    trySend(detectedSteps)
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    Log.d("SensorRepository", "Accuracy changed to: $accuracy")
                }
            }
        }

        val supportedAndEnabled = sensorManager.registerListener(listener,
            detectStepsSensor, SensorManager.SENSOR_DELAY_UI)
        Log.d("SensorRepository", "Detected Steps Sensor listener registered: $supportedAndEnabled")

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }.conflate()*/
}