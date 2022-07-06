package com.example.mobile.julfani.tubes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

    final String channelidStr = "notification";
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        /* set statusbar transparent */
        Utils.fullScreen(this);

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

        Utils.hideNavbar(this);

        createNotification();
    }

    public void createNotification(){

        builder = new NotificationCompat.Builder(this, channelidStr)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("better sleep")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setDefaults(NotificationManager.IMPORTANCE_LOW);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "better sleep";
            String description = "sleep in "+ minutes;
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel(channelidStr, name, importance);
            channel.setDescription(description);
            channel.setVibrationPattern(new long[]{0});
            channel.enableVibration(true);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());

    }


    public void launchMainActivity (View view){
        handler.removeCallbacks(runnable());
        run = false;

        notificationManager.deleteNotificationChannel(channelidStr);
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

                /* update notification  */
                builder.setContentText(result);
                notificationManager.notify(1, builder.build());

                minutes -= 1;
                if(run) handler.postDelayed(this, 1000);

                if(minutes <= 0){
                    result = "finish";
                    currentTimeTextView.setText(result);

                    /* update notification  */
                    builder.setContentText(result);
                    notificationManager.notify(1, builder.build());

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