package com.nursyah.bettersleep.utils

import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.util.*

object Utils {
    @JvmStatic
    fun fullScreen(appCompatActivity: AppCompatActivity){
        appCompatActivity.window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    @JvmStatic
    fun getDate(): String{
        val day = Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        val month = Calendar.getInstance()[Calendar.MONTH] + 1
        val year = Calendar.getInstance()[Calendar.YEAR]

        return String.format("%02d/%02d/%d", day,month,year)
    }

    @JvmStatic
    fun formatTime(seconds: Int): String {
        return if (seconds >= 3600) (seconds / 3600 % 24).toString() + "h " + seconds / 60 % 60 + "m " + seconds % 60 + "s" else (seconds / 60 % 60).toString() + "m " + seconds % 60 + "s"
    }

    const val EXTRAS_ALARM_TIME = "EXTRAS_ALARM_TIME"
    const val COLOR_WHITE = "#FFFFFFFF"
    const val COLOR2 = "#9D91FF"
}