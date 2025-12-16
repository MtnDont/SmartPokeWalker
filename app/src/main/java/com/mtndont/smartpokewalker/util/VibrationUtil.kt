package com.mtndont.smartpokewalker.util

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object VibrationUtil {
    fun vibrationEffectForEvolveScreen(context: Context) {
        // Linear Up, exponential down
        val amplitudes = intArrayOf(
            20, 40, 60, 80, 100,
            120, 140, 160, 180, 200,
            220, 240, 255, 127, 64,
            32, 16, 8, 4, 2,
            0
        )
        val timings = longArrayOf(
            461, 461, 461, 461, 461,
            461, 461, 461, 461, 461,
            461, 461, 461, 384, 192,
            96, 50, 50, 50, 50,
            50
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(
                VibrationEffect.createWaveform(
                    timings, amplitudes, -1
                )
            )
        }
        else {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.vibrate(
                CombinedVibration.createParallel(
                    VibrationEffect.createWaveform(
                        timings, amplitudes, -1
                    )
                )
            )
        }
    }
}