package com.example.mobile.julfani.tubes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.utils.Utils;

public class MoonActivity extends AppCompatActivity {

    public static String SHAREDPREFERENCES = "IDVALUE";
    public static String SHAREDPREFERENCES_LOOP = "IDVALUE_LOOP";
    public static String SHAREDPREFERENCES_RAISESOUND = "IDVALUE_RAISESOUND";
    public static String SHAREDPREFERENCES_ALARMSOUND = "IDVALUE_ALARMSOUND";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button raise_sound, loop, alarmdefault, alarmcustom;

    static boolean loop_pref;
    static boolean raisesound_pref;
    static Toast toast;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon);

        /* set statusbar transparent */
        Utils.fullScreen(this);
        Utils.hideNavbar(this);

        /* set moon */
        ImageView imageView = findViewById(R.id.moon_activity);
        imageView.setImageResource(R.drawable.mooniconselect);


        /* see home */
        Utils.exitApp(this);

        /* see stats */
        findViewById(R.id.stats_activity).setOnClickListener(v ->{
            finish();
            startActivity(new Intent(this, StatsActivity .class));
        });

        /* open link github */
        findViewById(R.id.imageView_github).setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nursyah21/bettersleep"));
            startActivity(browserIntent);
        });


        sharedPreferences = getSharedPreferences(SHAREDPREFERENCES, 0);
        editor = sharedPreferences.edit();
        loop_pref = sharedPreferences.getBoolean(SHAREDPREFERENCES_LOOP, false);
        raisesound_pref = sharedPreferences.getBoolean(SHAREDPREFERENCES_RAISESOUND, false);


        /* loop */
        loop = findViewById(R.id.loop);
        if(loop_pref)loop.setTextColor(getResources().getColor(R.color.white));

        loop.setOnClickListener(v->set_loop());

        /* raise sound */
        raise_sound = findViewById(R.id.raise_sound);
        if(raisesound_pref) raise_sound.setTextColor(getResources().getColor(R.color.white));

        raise_sound.setOnClickListener(v->set_raise_sound());


        /* set alarm default*/
        alarmdefault = findViewById(R.id.alarm_sound_default_actMoon);
        alarmdefault.setOnClickListener(v->setAlarmDefault());

        /* set alarm custom */
        alarmcustom = findViewById(R.id.alarm_sound_custom_actMoon);
        alarmcustom.setOnClickListener(v->setAlarmCustom());

        /* set color alarm */
        String ringtone_default = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
        String ringtone_alarm = sharedPreferences.getString(SHAREDPREFERENCES_ALARMSOUND, ringtone_default);

        if(ringtone_alarm.equals(ringtone_default)){
            alarmcustom.setTextColor(getResources().getColor(R.color.black));
            alarmdefault.setTextColor(getResources().getColor(R.color.white));
        }else{
            alarmcustom.setTextColor(getResources().getColor(R.color.white));
            alarmdefault.setTextColor(getResources().getColor(R.color.black));
        }

    }

    private void setAlarmCustom(){
        Uri uri = Uri.fromFile(Environment.getExternalStorageDirectory());

        intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(uri, "*/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = null;
        if(data != null)uri = data.getData();

        if(uri != null) {
            if (toast != null) toast.cancel();

            try {
                MediaPlayer.create(this, uri).getDuration();

                alarmcustom.setTextColor(getResources().getColor(R.color.white));
                alarmdefault.setTextColor(getResources().getColor(R.color.black));

                editor.putString(SHAREDPREFERENCES_ALARMSOUND, uri.toString());
                editor.commit();

                toast = Toast.makeText(this, "custom alarm sound", Toast.LENGTH_SHORT);
            }catch (Exception exception){
                toast = Toast.makeText(this, "format not support", Toast.LENGTH_SHORT);
            }

            toast.show();
        }

    }

    private void setAlarmDefault(){
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        alarmcustom.setTextColor(getResources().getColor(R.color.black));
        alarmdefault.setTextColor(getResources().getColor(R.color.white));

        editor.putString(SHAREDPREFERENCES_ALARMSOUND, uri.toString());
        editor.commit();
        if(toast != null) toast.cancel();

        toast = Toast.makeText(this, "default alarm sound", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void set_loop(){
        String status;
        int color;
        if(loop_pref) {
            color = getResources().getColor(R.color.black);
            status = "loop disable";
            editor.putBoolean(SHAREDPREFERENCES_LOOP, false);
            loop_pref = false;
        }else{
            color = getResources().getColor(R.color.white);
            status = "loop enable";
            editor.putBoolean(SHAREDPREFERENCES_LOOP, true);
            loop_pref = true;
        }

        if(editor.commit()){
            loop.setTextColor(color);
            if(toast != null)toast.cancel();
            toast = Toast.makeText(this, status, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void set_raise_sound(){
        String status;
        int color;
        if(raisesound_pref) {
            color = getResources().getColor(R.color.black);
            status = "raise sound disable";
            editor.putBoolean(SHAREDPREFERENCES_RAISESOUND, false);
            raisesound_pref = false;
        }else{
            color = getResources().getColor(R.color.white);
            status = "raise sound enable";
            editor.putBoolean(SHAREDPREFERENCES_RAISESOUND, true);
            raisesound_pref = true;
        }

        if(editor.commit()){
            raise_sound.setTextColor(color);
            if(toast != null)toast.cancel();
            toast = Toast.makeText(this, status, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if(toast != null)toast.cancel();

    }
}


