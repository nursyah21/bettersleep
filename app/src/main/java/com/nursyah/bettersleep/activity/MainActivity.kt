package com.nursyah.bettersleep.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import com.nursyah.bettersleep.R
import com.nursyah.bettersleep.utils.Utils
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utils.fullScreen(this)

        /* moon activity */
        findViewById<ImageView>(R.id.options_imgview).setOnClickListener{
            startActivity(Intent(this, MoonActivity::class.java))
        }

        /* alarm activity */
        findViewById<ImageView>(R.id.alarm_imgview).setOnClickListener{
            startActivity(Intent(this, AlarmActivity::class.java))
        }

        /* stats activity */
        findViewById<ImageView>(R.id.stats_imgview).setOnClickListener{
            startActivity(Intent(this, StatsActivity::class.java))
        }

        runClock()
    }

    private fun runClock(){
        val alarmTextview = findViewById<TextView>(R.id.alarm_textview)
        val handler = Handler(Looper.getMainLooper())

        handler.post(object: Runnable{
            override fun run() {
                val hours = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
                val minutes = Calendar.getInstance()[Calendar.MINUTE]
                val time = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
                alarmTextview.text = time
                handler.postDelayed(this, 1000)
            }
        })
    }
}