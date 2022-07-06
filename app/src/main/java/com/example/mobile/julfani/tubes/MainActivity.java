package com.example.mobile.julfani.tubes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView currentTimeTextView;
    private Button alarmButton;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* set statusbar transparent */
        Utils.fullScreen(this);

        /* set alarms */
        findViewById(R.id.alarm_button).setOnClickListener(v-> startActivity(new Intent(MainActivity.this, AlarmActivity.class)));
        /* see statistics */
        findViewById(R.id.stats_activity).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatsActivity.class)));

        /*update set alarm*/
        alarmButton = findViewById(R.id.alarm_button);
        runClock();

        Utils.hideNavbar(this);


    }




    private void runClock(){
        currentTimeTextView =findViewById(R.id.current_time);

        handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minutes = Calendar.getInstance().get(Calendar.MINUTE);

                String time = String.format(Locale.getDefault(), "%02d : %02d", hours, minutes);

                currentTimeTextView.setText(time);
                hours = (hours + 3) % 24;
                alarmButton.setText(String.format(Locale.getDefault(),"%02d:%02d", hours, minutes));

                handler.postDelayed(this, 1000);
            }
        });
    }
}