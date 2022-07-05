package com.example.mobile.julfani.tubes;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile.julfani.tubes.entity.Sleep;
import com.example.mobile.julfani.tubes.entity.SleepViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SleepActivity extends AppCompatActivity {
    private TextView currentTimeTextView;
    private Ringtone alarm;
    private static int minutes;
    private boolean run, found;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        /* set statusbar transparent */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        /* initialization */
        found = false;
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        alarm = RingtoneManager.getRingtone(this, alarmUri);
        currentTimeTextView = findViewById(R.id.time_remaining);
        minutes = (getIntent().getIntExtra(AlarmActivity.TIMESLEEP, 0) * 60) + ((60 - Calendar.getInstance().get(Calendar.SECOND)) % 60);
        run = true;
        handler = new Handler(Looper.getMainLooper());


        findViewById(R.id.end_sleep).setOnClickListener(this::launchMainActivity);

        /* run alarm */
        handler.post(runnable());
    }

    public void launchMainActivity (View view){
        handler.removeCallbacks(runnable());
        run = false;
        finish();

        if(found){
            alarm.stop();
            startActivity(new Intent(this, StatsActivity.class));
        }
    }

    private Runnable runnable(){
        return new Runnable() {
            @Override
            public void run() {
                String result;
                if(minutes > 3600) result = String.format(Locale.getDefault(), "%dh %02dm %02ds", (minutes / 3600)%24, (minutes /60)%60, (minutes %60));
                else result = String.format(Locale.getDefault(), "%dm %02ds", (minutes / 60) % 60, (minutes %60));

                currentTimeTextView.setText(result);


                minutes -= 1;
                if(run) handler.postDelayed(this, 1000);

                if(minutes <= 0){
                    result = "finish";
                    currentTimeTextView.setText(result);
                    Button button= findViewById(R.id.end_sleep);
                    button.setText(result);
                    handler.removeCallbacks(this);
                    found = true;
                    runAlarm();
                }
            }
        };
    }

    private void runAlarm(){
        int minutes = getIntent().getIntExtra(AlarmActivity.TIMESLEEP, 0);
        String date = DateFormat.getInstance().format(Calendar.getInstance().getTime());

        /* play music */
        alarm.play();

        /* save data */
        SleepViewModel sleepViewModel = new ViewModelProvider(this).get(SleepViewModel.class);
        Sleep newData = new Sleep(date + " " + minutes);
        sleepViewModel.insert(newData);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "please tap cancel button", Toast.LENGTH_SHORT).show();
    }
}