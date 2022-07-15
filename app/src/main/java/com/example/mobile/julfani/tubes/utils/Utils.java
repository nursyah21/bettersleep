package com.example.mobile.julfani.tubes.utils;

import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.activity.AlarmActivity;
import com.example.mobile.julfani.tubes.activity.MainActivity;
import com.example.mobile.julfani.tubes.activity.MoonActivity;
import com.example.mobile.julfani.tubes.activity.StatsActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class Utils {
    /* set alarms */
    public static void AlarmActivity(AppCompatActivity appCompatActivity){
        appCompatActivity.findViewById(R.id.alarm_button).setOnClickListener(v-> {

            appCompatActivity.startActivity(new Intent(appCompatActivity, AlarmActivity .class));
        });
    }

    /* back to home */
    public static void homeActivity(AppCompatActivity appCompatActivity){
        appCompatActivity.findViewById(R.id.alarm_button).setOnClickListener(v-> {

            appCompatActivity.startActivity(new Intent(appCompatActivity, MainActivity.class));
        });
    }

    /* close app */
    public static void exitApp(AppCompatActivity appCompatActivity){
        appCompatActivity.findViewById(R.id.home).setOnClickListener(v-> appCompatActivity.finish());
    }

    /* see statistics */
    public static void StatsActivity(AppCompatActivity appCompatActivity){
        appCompatActivity.findViewById(R.id.stats_activity).setOnClickListener(v -> appCompatActivity.startActivity(new Intent(appCompatActivity, StatsActivity .class)));
    }

    /* see moon */
    public static void MoonActivity(AppCompatActivity appCompatActivity){
        //appCompatActivity.finish();
        appCompatActivity.findViewById(R.id.moon_activity).setOnClickListener(v -> appCompatActivity.startActivity(new Intent(appCompatActivity, MoonActivity .class)) );
    }

    public static String getTime(){
        return DateFormat.getInstance().format(Calendar.getInstance().getTime()).split(" ")[1];
    }

    public static String getDate(){
        return DateFormat.getInstance().format(Calendar.getInstance().getTime()).split(" ")[0];
    }

    public static String formatTime(int seconds){
        return (seconds >= 3600 ) ? (seconds / 3600)%24 + "h " + (seconds / 60)%60 + "m " + seconds % 60 + "s"
                : (seconds / 60)%60 + "m " + (seconds % 60) + "s";
    }

    public static void fullScreen(AppCompatActivity appCompatActivity){
        /* set statusbar transparent */
        appCompatActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static void hideNavbar(AppCompatActivity appCompatActivity) {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(appCompatActivity.getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
    }

}
