package com.example.mobile.julfani.tubes.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.utils.Utils;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    public static final String TIMESLEEP = "timeSleep";
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        /* set statusbar transparent */
        Utils.fullScreen(this);

        /* initialize */
        timePicker = findViewById(R.id.timePicker);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            timePicker.setBackgroundColor(Color.TRANSPARENT);
        }

        /* set alarm */
        findViewById(R.id.start_sleeping).setOnClickListener(this::launchSleepingActivity);

        /* back to home */
        findViewById(R.id.home).setOnClickListener(v-> {
            finish();
        });

        /* see statistics */
        findViewById(R.id.stats_activity).setOnClickListener(v ->{
            finish();
            startActivity(new Intent(this, StatsActivity .class));
        });


        /* see moon */
        findViewById(R.id.moon_activity).setOnClickListener(v ->{
            finish();
            startActivity(new Intent(this, MoonActivity .class));
        });


        Utils.hideNavbar(this);
    }


    public void launchSleepingActivity(View view){
        finish();

        Intent intent = new Intent(this, SleepActivity.class);


        int currenttime = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 3600) +  (Calendar.getInstance().get(Calendar.MINUTE) * 60);
        int time = (timePicker.getCurrentHour() * 3600) + (timePicker.getCurrentMinute() * 60);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            time = (timePicker.getHour() * 3600) + (timePicker.getMinute() * 60);
        }

        int result = time - currenttime;
        result += (60 - Calendar.getInstance().get(Calendar.SECOND));

        if(result < 0) result += (24 * 3600);

        intent.putExtra(TIMESLEEP, result);

        startActivity(intent);
    }
}