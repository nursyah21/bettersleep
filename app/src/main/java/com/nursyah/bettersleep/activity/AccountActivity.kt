package com.nursyah.bettersleep.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.nursyah.bettersleep.R
import com.nursyah.bettersleep.entity.SleepViewModel
import com.nursyah.bettersleep.utils.Utils

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        Utils.fullScreen(this)

        val sleepViewModel = ViewModelProvider(this)[SleepViewModel::class.java]

        findViewById<Button>(R.id.deleteDatabaseButton).setOnClickListener{
            sleepViewModel.deleteAll()
            finish()
        }
    }
}