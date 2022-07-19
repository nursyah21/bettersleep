package com.example.mobile.julfani.tubes.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.utils.SleepListAdapter;
import com.example.mobile.julfani.tubes.utils.Utils;
import com.example.mobile.julfani.tubes.entity.Sleep;
import com.example.mobile.julfani.tubes.entity.SleepViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    SleepViewModel sleepViewModel;

    Button week, month, year;
    int white, black, options;
    SleepListAdapter sleepListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        /* set statusbar transparent */
        Utils.fullScreen(this);
        Utils.hideNavbar(this);


        /* set stats icon */
        ImageView imageView = findViewById(R.id.stats_activity);
        imageView.setImageResource(R.drawable.statisticsiconselect);

        /* recycle view */
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        sleepListAdapter = new SleepListAdapter(this);


        recyclerView.setAdapter(sleepListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* update data from sqlite */
        sleepViewModel = new ViewModelProvider(this).get(SleepViewModel.class);


        sleepViewModel.getAllData().observe(this, sleeps -> {
            statistics();
            setSleepListAdapter(sleeps);
        });


        /* confirm delete */
        findViewById(R.id.fab_delete).setOnClickListener(view ->
                startActivity(new Intent(this, ConfirmDeleteActivity.class)));

       /* back to home */
        findViewById(R.id.home).setOnClickListener(v-> finish());

        /* see moon */
        findViewById(R.id.moon_activity).setOnClickListener(v ->{
            finish();
            startActivity(new Intent(this, MoonActivity .class));
        });

        white = getResources().getColor(R.color.white);
        black = getResources().getColor(R.color.black);


        /* week */
        week =  findViewById(R.id.week_options);
        week.setOnClickListener(v -> setWeek());

        /* month */
        month = findViewById(R.id.month_options);
        month.setOnClickListener(v -> setMonth());

        /* year */
        year = findViewById(R.id.year_options);
        year.setOnClickListener(v -> setYear());

        /* getshared preferences */
        SharedPreferences sharedPreferences = getSharedPreferences("STATS_SHAREDPREFERENCE", 0);
        String options_shared = sharedPreferences.getString("STATS_OPT", "MONTH");
        setOptions(options_shared);
    }

    private static class sortCompare implements Comparator<Sleep>{
        private String replace(String s){
            s = s.replaceAll("^([0-9])/","0$1/");
            s = s.replaceAll("/([0-9])/","/0$1/");
            return s;
        }

        @Override
        public int compare(Sleep sleep, Sleep t1) {
            String s1 = replace(sleep.getData());
            String s2 = replace(t1.getData());

            return s1.compareTo(s2);
        }
    }

    private void setSleepListAdapter(List<Sleep> sleepList){
        Collections.sort(sleepList, new sortCompare());
        sleepListAdapter.setSleepList(sleepList);
    }

    private void setOptions(String options_shared){
        switch (options_shared){
            case "WEEK":
                setWeek();
                break;
            case "MONTH":
                setMonth();
                break;
            case "YEAR":
                setYear();
                break;
        }
    }

    private void setWeek(){
        options = 7;
        week.setTextColor(white);
        month.setTextColor(black);
        year.setTextColor(black);
        statistics();
    }
    private void setMonth(){
        options = 30;
        week.setTextColor(black);
        month.setTextColor(white);
        year.setTextColor(black);
        statistics();
    }
    private void setYear(){
        options = 365;
        week.setTextColor(black);
        month.setTextColor(black);
        year.setTextColor(white);
        statistics();
    }


    @SuppressLint("SetTextI18n")
    private void statistics(){
        TextView avg = findViewById(R.id.avg_duration);
        TextView max = findViewById(R.id.max_duration);

        List<Sleep> sleepList = sleepViewModel.getAllData().getValue();

        if(sleepList == null) return;
        /* get max, avg based options */
        Collections.sort(sleepList, new sortCompare());

        LinkedList<Integer> duration = new LinkedList<>();

        int total = 0;
        String date = "";
        for(Sleep sleep: sleepList){
            if(date.equals(""))date = sleep.getDate();

            total += sleep.getDuration();

            if(!sleep.getDate().equals(date)){
                duration.add(total);
                total = 0;
                date = sleep.getDate();
            }
        }
        if(duration.size() == 0) duration.add(total);

        int overflow = 0, max_var=0;
        total=0;
        for(Integer i : duration){
            total += i;
            overflow++;
            if(i > max_var) max_var = i;
            if(overflow >= options)break;
        }
        int avg_var = total / overflow;


        avg.setText("average:\n" + Utils.formatTime(avg_var));
        max.setText("max:\n" + Utils.formatTime(max_var));

    }

}