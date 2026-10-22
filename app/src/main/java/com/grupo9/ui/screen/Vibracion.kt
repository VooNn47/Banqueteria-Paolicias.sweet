package com.grupo9.ui.screen

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager




fun vibrar(context: Context, milisegundos: Long) {
    val vibrador = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        manager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    if (vibrador.hasVibrator()) {
        vibrador.vibrate(
            VibrationEffect.createOneShot(
                milisegundos,
                255
            )
        )

    }
}