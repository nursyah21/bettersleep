package com.example.mobile.julfani.tubes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    public static final String TIMESLEEP = "timeSleep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        /* set statusbar transparent */
        Utils.fullScreen(this);

        /* set timepicker */
        setTimePicker();


        findViewById(R.id.start_sleeping).setOnClickListener(this::launchSleepingActivity);

        Utils.hideNavbar(this);
    }

    private void setTimePicker(){
        TimePicker timePicker = findViewById(R.id.timePicker);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);

        hour = (hour + 3) % 24;

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minutes);
    }


    public void launchSleepingActivity(View view){
        finish();

        Intent intent = new Intent(this, SleepActivity.class);
        TimePicker timePicker = findViewById(R.id.timePicker);

        int currenttime = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60) +  (Calendar.getInstance().get(Calendar.MINUTE));
        int time = (timePicker.getCurrentHour() * 60) + timePicker.getCurrentMinute();
        int result = time - currenttime;
        if(result < 0) result += (24 * 60);

        intent.putExtra(TIMESLEEP, result);

        startActivity(intent);
    }
}