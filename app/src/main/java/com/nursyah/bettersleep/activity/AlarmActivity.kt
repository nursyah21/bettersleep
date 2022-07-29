package com.nursyah.bettersleep.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.nursyah.bettersleep.R
import com.nursyah.bettersleep.utils.Utils
import java.util.*

class AlarmActivity : AppCompatActivity() {
    private var timepicker:TimePicker?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        Utils.fullScreen(this)

        timepicker = findViewById(R.id.timepicker)
        timepicker?.setIs24HourView(true)

        findViewById<Button>(R.id.sleep_button).setOnClickListener{ startSleep() }
    }

    private fun getTime(): Int{
        val timeNow = Calendar.getInstance()[Calendar.HOUR_OF_DAY] * 3600 + Calendar.getInstance()[Calendar.MINUTE] * 60
        @Suppress("DEPRECATION") val alarm = timepicker!!.currentHour * 3600 + timepicker!!.currentMinute * 60

        val result = alarm - timeNow
        return if(result < 0) result + 24 * 3600 else result
    }

    private fun startSleep(){
        val intent = Intent(this, SleepActivity::class.java)
        intent.putExtra(Utils.EXTRAS_ALARM_TIME, getTime())
        startActivity(intent)
        finish()
    }
}