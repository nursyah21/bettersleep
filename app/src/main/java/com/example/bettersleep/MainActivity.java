package com.example.bettersleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private TextView alarmTextview;
    private Button alarmBtn;
    private TimePicker timePicker;

    private SharedPreferences preferences;

    public final static String ExtraSleepTimeHour = "SleepTimeExtraHour";
    public final static String ExtraSleepTimeMinutes = "SleepTimeExtraMinutes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("sharedprefs", MODE_PRIVATE);

        if(preferences.getBoolean("sleep", false)) {
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
        }

        alarmTextview = findViewById(R.id.timeTextView);
        alarmBtn = findViewById(R.id.setAlarmBtn);
        timePicker = findViewById(R.id.alarmTimePicker);

        alarmBtn.setOnClickListener(this::runSleepActivity);

        runClock();


    }

    private void runSleepActivity(View view){
        int hours = timePicker.getCurrentHour();
        int minutes = timePicker.getCurrentMinute();

        int diffHour = hours - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int diffMinutes = minutes - Calendar.getInstance().get(Calendar.MINUTE);

        if( ((diffHour*60) + diffMinutes) < 0){
            Toast.makeText(this, "alarm can't be set to past", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, AlarmActivity.class);

        intent.putExtra(ExtraSleepTimeHour, hours);
        intent.putExtra(ExtraSleepTimeMinutes, minutes);

        startActivity(intent);
    }


    private void runClock(){
        handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {

                String dateStr = DateFormat.getInstance().format(Calendar.getInstance().getTime()).split(" ")[1];

                alarmTextview.setText(dateStr);
                handler.postDelayed(this, 1000);
            }
        });
    }

}