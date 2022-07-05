package com.example.mobile.julfani.tubes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.mobile.julfani.tubes.entity.SleepViewModel;

public class ConfirmDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delete);

        findViewById(R.id.deleteDatabaseButton).setOnClickListener(view -> {
            SleepViewModel sleepViewModel = new ViewModelProvider(this).get(SleepViewModel.class);
            sleepViewModel.deleteAll();

            finish();
        });

        findViewById(R.id.cancel_button_confirmdelete).setOnClickListener(view -> finish());
    }
}