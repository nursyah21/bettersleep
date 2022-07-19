package com.example.mobile.julfani.tubes.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.utils.Utils;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    boolean close=false;
    Toast toast;

    @SuppressLint({"CommitPrefEdits", "ApplySharedPref"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* show thankyou first time */
        SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREFERENCES_MAIN", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean thankyou = sharedPreferences.getBoolean("THANKYOU_SHARED_PREFERENCES_MAIN", true);

        if(thankyou){
            startActivity(new Intent(this, ThankYouActivity.class));
            editor.putBoolean("THANKYOU_SHARED_PREFERENCES_MAIN", false);
            editor.commit();
        }

        /* set statusbar transparent */
        Utils.fullScreen(this);
        Utils.hideNavbar(this);

        /* set home icon */
        ImageView imageView = findViewById(R.id.home);
        imageView.setImageResource(R.drawable.homeiconselect);

        /* set alarms */
        findViewById(R.id.alarm_button).setOnClickListener(v-> startActivity(new Intent(this, AlarmActivity .class)));

        /* close app */
        findViewById(R.id.home).setOnClickListener(v-> {
            if(close) {
                if(toast != null) toast.cancel();
                finish();
            }
            backtofalse();
        });

        /* see statistics */
        findViewById(R.id.stats_activity).setOnClickListener(v ->
                startActivity(new Intent(this, StatsActivity .class)));


        /* see moon */
        findViewById(R.id.moon_activity).setOnClickListener(v ->
                startActivity(new Intent(this, MoonActivity .class)) );

        runClock();

    }

    private void backtofalse(){
        close = true;
        if(toast != null) toast.cancel();
        toast = Toast.makeText(this, "tap again to close", Toast.LENGTH_SHORT);
        toast.show();


        Handler handler = new Handler();
        handler.postDelayed(() -> close = false, 3000);
    }

    private void runClock(){
        TextView currentTimeTextView =findViewById(R.id.current_time);
        TextView alarm_button = findViewById(R.id.alarm_button);

        Handler handler = new Handler(Looper.getMainLooper());


        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minutes = Calendar.getInstance().get(Calendar.MINUTE);

                String time = String.format(Locale.getDefault(), "%02d : %02d", hours, minutes);

                currentTimeTextView.setText(time);
                alarm_button.setText(time);

                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        if(toast != null) toast.cancel();
    }


}