package com.example.mobile.julfani.tubes.activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.entity.Sleep;
import com.example.mobile.julfani.tubes.entity.SleepViewModel;
import com.example.mobile.julfani.tubes.utils.Utils;

public class SleepActivity extends AppCompatActivity {
    private TextView currentTimeTextView;
    private static int seconds, play,interval;
    private boolean run, found, options;
    private RelativeLayout mainsleep, optionsleep;
    MediaPlayer mediaPlayer;

    private Sleep savedata;

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
        Utils.hideNavbar(this);

        /* initialization */
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer = MediaPlayer.create(this, uri);
        currentTimeTextView = findViewById(R.id.time_remaining);
        seconds = getIntent().getIntExtra(AlarmActivity.TIMESLEEP, 0);

        run=true;
        found=false;
        options=false;

        play=1;
        interval=1;

        savedata = new Sleep(Utils.getDate(), Utils.getTime(), seconds);
        handler = new Handler(Looper.getMainLooper());

        mainsleep = findViewById(R.id.mainsleep);
        optionsleep = findViewById(R.id.options_layout);

        findViewById(R.id.end_sleep).setOnClickListener(this::launchMainActivity);


        /* options sleep */
        EditText set_play = findViewById(R.id.set_play_edittext);
        EditText set_interval = findViewById(R.id.set_interval_edittext);

        findViewById(R.id.set_alarm).setOnClickListener(v->{
            String set_playStr = set_play.getText().toString();
            String set_intervalStr = set_interval.getText().toString();
            int new_play = 0, new_interval = 0;

            if(TextUtils.isDigitsOnly(set_playStr)) new_play = Integer.parseInt(set_playStr);
            if(TextUtils.isDigitsOnly(set_intervalStr)) new_interval = Integer.parseInt(set_intervalStr);


                play = new_play;
                interval = new_interval;
                options = false;

                mainsleep.setVisibility(View.INVISIBLE);
                optionsleep.setVisibility(View.VISIBLE);

        });

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
            String description = "sleep in "+ seconds;
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
            mediaPlayer.stop();
            handler.removeCallbacks(runnable());
            startActivity(new Intent(this, StatsActivity.class));
        }
    }

    /*  */
    private Runnable runnable(){
        return new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                String result = Utils.formatTime(seconds);

                currentTimeTextView.setText(result);

                /* update notification  */
                builder.setContentText(result);
                notificationManager.notify(1, builder.build());

                if(!options) {
                    seconds -= 1;
                }

                if(run) handler.postDelayed(this, 1000);

                if(seconds <= 0){
                    result = "-" + Utils.formatTime(-1 * seconds);
                    currentTimeTextView.setText(result);
                    currentTimeTextView.setTextColor(Color.red(1));
                    /* update notification  */
                    builder.setContentText(result);
                    notificationManager.notify(1, builder.build());
                    Button button= findViewById(R.id.end_sleep);
                    button.setText("finish");
                    found = true;
                    runAlarm();
                }
            }
        };
    }

    private void runAlarm(){
        handler.post(loopAlarm());

        /* save data */
//        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.ADJUST_RAISE);

        SleepViewModel sleepViewModel = new ViewModelProvider(this).get(SleepViewModel.class);
        sleepViewModel.insert(savedata);
    }

    private Runnable loopAlarm(){
        return new Runnable(){
            @Override
            public void run() {
                /* play music */

                mediaPlayer.setVolume(1, 1);
                mediaPlayer.start();
                handler.postDelayed(this, mediaPlayer.getDuration() + (long) interval * 60 * 1000);
                play--;
                if(play == 0) handler.removeCallbacks(this);
            }
        };
    }

    @Override
    public void onBackPressed() {
        String res = (options) ? "please tap enter" : "please tap cancel button";
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();

    }
}