package com.nursyah.bettersleep.activity

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nursyah.bettersleep.R
import com.nursyah.bettersleep.entity.Sleep
import com.nursyah.bettersleep.entity.SleepViewModel
import com.nursyah.bettersleep.utils.Utils
import java.util.*

class SleepActivity : AppCompatActivity() {
    private var mediaPlayer:MediaPlayer?= null
    private var status:TextView?= null
    private var alarmTime = 0
    private var duration = 0
    private var toast:Toast?= null
    private var onpause = false
    private var stopAlarm = false
    private var showNotif = true
    private var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)

        Utils.fullScreen(this)

        status = findViewById(R.id.status)
        date = Utils.getDate()
        alarmTime = intent.getIntExtra(Utils.EXTRAS_ALARM_TIME, 0) + 60 - Calendar.getInstance()[Calendar.SECOND]

        findViewById<Button>(R.id.finisBtn).setOnClickListener{finishAction()}
        findViewById<Button>(R.id.cancelBtn).setOnClickListener{cancelAction()}

        runClock()
    }

    private fun finishAction(){
        mediaPlayer?.stop()
        stopAlarm = true
        showNotif = false
        startActivity(Intent(this, StatsActivity::class.java))

        duration = if(duration >= alarmTime) alarmTime else duration

        val sleepViewModel = ViewModelProvider(this)[SleepViewModel::class.java]

        sleepViewModel.insert(Sleep(0, date, duration))

        Log.d("test", "$date $duration")

        finish()
        finish()
    }

    private fun cancelAction(){
        stopAlarm = true
        showNotif = false
        mediaPlayer?.stop()
        finish()
    }

    private fun runClock(){
        val timeRemaining:TextView = findViewById(R.id.time_remaining)
        var alarmTimeLocal = alarmTime
        var increment = -1
        val changeStatus = "wake up"
        val handler = Handler(Looper.getMainLooper())

        handler.post(object: Runnable {
            override fun run() {
                timeRemaining.text = Utils.formatTime(alarmTimeLocal)
                if(alarmTimeLocal == 0){
                    increment = 1
                    status?.text = changeStatus
                    playAlarm()
                }

                if(!onpause) {
                    alarmTimeLocal += increment
                    if(increment == -1) duration++
                }

                if(stopAlarm) return
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun playAlarm(){
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.ADJUST_RAISE)

        mediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        mediaPlayer?.isLooping = true
        mediaPlayer?.setVolume(1f, 1f)
        mediaPlayer?.start()
    }

    override fun onBackPressed() {
        notify("please press cancel or finish to back")
    }

    override fun onResume() {
        super.onResume()
        onpause = false
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        onpause = true
        if(showNotif)notify("please back to app to resume countdown")
    }

   private fun notify(str: String) {
       toast?.cancel()
       toast = Toast.makeText(this, str, Toast.LENGTH_SHORT)
       toast?.show()
   }
}