package com.example.mobile.julfani.tubes.activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
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

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.entity.Sleep;
import com.example.mobile.julfani.tubes.entity.SleepViewModel;
import com.example.mobile.julfani.tubes.utils.Utils;

public class SleepActivity extends AppCompatActivity {
    private TextView currentTimeTextView;
    private static int seconds, play,interval;
    private boolean run, found, options;
    MediaPlayer mediaPlayer;

    private Sleep savedata;

    final String channelidStr = "notification";
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;

    Handler handler;

    static boolean loop, raisesound, cancelnotif=false;
    TextView loopBtn, raiseSoundBtn;
    static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        /* set statusbar transparent */
        Utils.fullScreen(this);
        Utils.hideNavbar(this);

        /* initialization */

        currentTimeTextView = findViewById(R.id.time_remaining);
        seconds = getIntent().getIntExtra(AlarmActivity.TIMESLEEP, 0);

        run=true;
        found=false;
        options=false;

        play=1;
        interval=1;

        savedata = new Sleep(Utils.getDate(), Utils.getTime(), seconds);
        handler = new Handler(Looper.getMainLooper());

        findViewById(R.id.end_sleep).setOnClickListener(this::launchMainActivity);

        /* get shared preferences */
        SharedPreferences sharedPreferences = getSharedPreferences(MoonActivity.SHAREDPREFERENCES, 0);
        loop = sharedPreferences.getBoolean(MoonActivity.SHAREDPREFERENCES_LOOP, false);
        raisesound = sharedPreferences.getBoolean(MoonActivity.SHAREDPREFERENCES_RAISESOUND, false);

        loopBtn = findViewById(R.id.loop_sleepAct);
        raiseSoundBtn = findViewById(R.id.raise_sound_sleepAct);
        loopBtn.setOnClickListener(this::setLoopBtn);
        raiseSoundBtn.setOnClickListener(this::setRaiseSoundBtn);

        /* alarm sound */
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        String getUri = sharedPreferences.getString(MoonActivity.SHAREDPREFERENCES_ALARMSOUND, uri.toString());
        uri = Uri.parse(getUri);

        mediaPlayer = MediaPlayer.create(this, uri);

        if(loop){
            loopBtn.setTextColor(getResources().getColor(R.color.white));
        }
        if(raisesound){
            raiseSoundBtn.setTextColor(getResources().getColor(R.color.white));
        }

        /* run alarm */
        handler.post(runnable());

        Utils.hideNavbar(this);

        createNotification();
    }


    public void setRaiseSoundBtn(View view){
        String status;
        if(raisesound){
            raiseSoundBtn.setTextColor(getResources().getColor(R.color.black));
            raisesound = false;
            status = "raise sound disable";
        }else{
            raiseSoundBtn.setTextColor(getResources().getColor(R.color.white));
            raisesound = true;
            status = "raise sound enable";
        }

        if(toast != null)toast.cancel();
        toast = Toast.makeText(this, status, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setLoopBtn(View view){
        String status;
        if(loop){
            loopBtn.setTextColor(getResources().getColor(R.color.black));
            loop = false;
            play = Integer.MAX_VALUE;
            status = "loop disable";
        }else{
            loopBtn.setTextColor(getResources().getColor(R.color.white));
            loop = true;
            play = 1;
            status = "loop enable";
        }


        if(toast != null)toast.cancel();
        toast = Toast.makeText(this, status, Toast.LENGTH_SHORT);
        toast.show();
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
        finish();

        if(found){
            mediaPlayer.stop();
            handler.removeCallbacks(runnable());
            startActivity(new Intent(this, StatsActivity.class));
            cancelnotif = true;
        }

        notificationManager.deleteNotificationChannel(channelidStr);
        notificationManager.cancelAll();
    }

    /* runnable */
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
                else if(!found && !mediaPlayer.isPlaying()) notificationManager.cancelAll();

                if(seconds <= 0){
                    result = "Wake Up\n" + Utils.formatTime(-1 * seconds);
                    currentTimeTextView.setText(result);

                    /* update notification  */
                    builder.setContentText(result);

                    notificationManager.notify(1, builder.build());

                    Button button= findViewById(R.id.end_sleep);
                    button.setText("finish");
                    found = true;
                    runAlarm();
                    if(cancelnotif)notificationManager.cancelAll();
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    private void runAlarm(){
        handler.post(loopAlarm());

        /* save data */
        if(raisesound){
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.ADJUST_RAISE);
        }


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
        if(toast != null)toast.cancel();
        toast = Toast.makeText(this, res, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void finish() {
        super.finish();
        if(toast != null)toast.cancel();
    }
}