package com.example.bettersleep;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {

    private TextView timeSleepTextview;
    private TextView currentTimeTextView;
    private Button alarmBtn;
    private Ringtone alarm;

    private SharedPreferences sharedPreferences;

    /* data from main activity */
    private int hours;
    private int minutes;


    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        sharedPreferences = getSharedPreferences("sharedPrefences", MODE_PRIVATE);

        alarmBtn = findViewById(R.id.alarmBtn);
        currentTimeTextView = findViewById(R.id.currentTimeTextView);
        timeSleepTextview = findViewById(R.id.timeSleepTextView);


        /* get data from main activity */
        hours = getIntent().getIntExtra(MainActivity.ExtraSleepTimeHour, 0);
        minutes = getIntent().getIntExtra(MainActivity.ExtraSleepTimeMinutes, 0);


        timeSleepTextview.setText(String.format(Locale.getDefault(),"%02d:%02d", hours, minutes));

        /* Alarm */
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        alarm = RingtoneManager.getRingtone(this, ringtoneUri);

        alarmBtn.setOnClickListener(this::playAlarm);

        runClock();
    }


    private void runClock(){
        handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {

                int diffHours = hours - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int diffMinutes = minutes - Calendar.getInstance().get(Calendar.MINUTE);

                String timeremaining = String.format(Locale.getDefault(),"%02d h %02d m", diffHours, diffMinutes);

                currentTimeTextView.setText(timeremaining);

                handler.postDelayed(this, 1000);
                if(diffHours + diffMinutes <= 0){
                    playAlarm(alarmBtn);
                    handler.removeCallbacks(this);
                }
            }
        });
    }

    private void playAlarm(View view){
        String status;
        if(alarm.isPlaying()){
            alarm.stop();
            status = "play";
        }else{
            alarm.play();
            status = "pause";
        }

        alarmBtn.setText(status);
    }

}