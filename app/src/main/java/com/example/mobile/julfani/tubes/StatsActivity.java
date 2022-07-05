package com.example.mobile.julfani.tubes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.julfani.tubes.entity.Sleep;
import com.example.mobile.julfani.tubes.entity.SleepViewModel;

import java.util.LinkedList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    SleepViewModel sleepViewModel;
    TextView avg, today, max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        /* set statusbar transparent */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        /* recycle view */
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        SleepListAdapter sleepListAdapter = new SleepListAdapter(this);

        recyclerView.setAdapter(sleepListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* initialization  */
        avg = findViewById(R.id.avg_duration);
        today = findViewById(R.id.today_duration);
        max = findViewById(R.id.max_duration);

        /* update data from sqlite */
        sleepViewModel = new ViewModelProvider(this).get(SleepViewModel.class);

        sleepViewModel.getAllData().observe(this, sleeps -> {
            statistics();
            sleepListAdapter.setSleepList(sleeps);
        });

        /* onclick */
        findViewById(R.id.fab_delete).setOnClickListener(view -> startActivity(new Intent(this, ConfirmDeleteActivity.class)));
    }

    @SuppressLint("SetTextI18n")
    private void statistics(){
        List<Sleep> sleepList = sleepViewModel.getAllData().getValue();

        if(sleepList == null){
            max.setText("max: 0");
            today.setText("today: 0");
            avg.setText("average: 0");
            return;
        }

        LinkedList<Integer> listDuration = new LinkedList<>();
        boolean firstData = true;
        String date = "";
        int total = 0;

        /* find average */
        for(Sleep i: sleepList){
            if(firstData){
                date = i.getData().split(" ")[0];
                firstData = false;
            }
            int temp = 0;

            /* different length array from platform, sometimes use ampm sometime use 24hour*/
            int lengthtempStr = i.getData().split(" ").length;
            String tempStr = i.getData().split(" ")[lengthtempStr - 1];

            if(TextUtils.isDigitsOnly(tempStr)) temp = Integer.parseInt(tempStr);
            total += temp;

            String newDate = i.getData().split(" ")[0];

            if (!date.equals(newDate)){
                listDuration.add(total);
                total = 0;
            }
        }

        if(listDuration.size() == 0) listDuration.add(total);

        /* update average, today, max */
        int todayVal = listDuration.get(0);
        int maxVal = todayVal;
        int averageVal = 0;

        for(int i : listDuration){
            if(i > maxVal)maxVal = i;
            averageVal += i;
        }

        averageVal /= listDuration.size();

        today.setText("today: " + (todayVal >= 60 ? (todayVal / 60)+"h "+(todayVal % 60) + "m" : todayVal + "m"));
        avg.setText("average: " + (averageVal >= 60 ? (averageVal / 60) + "h " + (todayVal % 60) + "m" : todayVal + "m"));
        max.setText("max: " + (maxVal >= 60 ? (maxVal / 60) + "h " + (maxVal % 60) + "m" : maxVal + "m"));
    }



}