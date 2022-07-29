package com.nursyah.bettersleep.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nursyah.bettersleep.R
import com.nursyah.bettersleep.entity.Sleep
import com.nursyah.bettersleep.entity.SleepViewModel
import com.nursyah.bettersleep.utils.SleepListAdapter
import com.nursyah.bettersleep.utils.Utils
import java.util.*

class StatsActivity : AppCompatActivity() {
    private var weekButton:Button?= null
    private var monthButton:Button?= null
    private var yearButton:Button?= null
    private var calculate = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        Utils.fullScreen(this)
        weekButton = findViewById(R.id.week_options)
        monthButton = findViewById(R.id.month_options)
        yearButton = findViewById(R.id.year_options)

        findViewById<ImageView>(R.id.person_img).setOnClickListener{ startActivity(Intent(this, AccountActivity::class.java)) }

        weekButton?.setOnClickListener { options(Utils.COLOR_WHITE, Utils.COLOR2, Utils.COLOR2, 7) }
        monthButton?.setOnClickListener { options(Utils.COLOR2, Utils.COLOR_WHITE, Utils.COLOR2, 30) }
        yearButton?.setOnClickListener { options(Utils.COLOR2, Utils.COLOR2, Utils.COLOR_WHITE, 365) }

        val sleepViewModel = ViewModelProvider(this)[SleepViewModel::class.java]
        sleepViewModel.getAllData().observe(this){sleeps->
            stats(sleeps)
        }
    }

    private fun stats(listSleep: List<Sleep>){
        val recycleViewSleep:RecyclerView = findViewById(R.id.recycleview_sleep)
        val sleepListAdapter = SleepListAdapter(this)
        recycleViewSleep.adapter = sleepListAdapter
        recycleViewSleep.layoutManager = LinearLayoutManager(this)

        val newListSleep = LinkedList<Sleep>()

        var date = ""
        var total = 0
        for(i in listSleep){
            total += i.duration
            if(date == "")date = i.date
            else if(date != i.date){
                newListSleep.push(Sleep(0, date, total))
                date = ""
                total = 0
            }
        }
        if(newListSleep.size == 0) newListSleep.push(Sleep(0, date, total))

        sleepListAdapter.setSleepList(newListSleep)

        val avgDuration:Button = findViewById(R.id.avg_duration)
        val maxDuration:Button = findViewById(R.id.max_duration)

        var max = 0
        total = 0

        for(i in newListSleep){
            if(i.duration > max) max = i.duration
            total += i.duration
            calculate--
            if(calculate == 0)break
        }

        val avgStr = "average:\n${Utils.formatTime(total / newListSleep.size)}"
        avgDuration.text = avgStr

        val maxStr = "max:\n${Utils.formatTime(max)}"
        maxDuration.text = maxStr
    }


    private fun options(week: String, month: String, year: String, cal: Int){
        weekButton?.setTextColor(Color.parseColor(week))
        monthButton?.setTextColor(Color.parseColor(month))
        yearButton?.setTextColor(Color.parseColor(year))
        calculate = cal
    }

}