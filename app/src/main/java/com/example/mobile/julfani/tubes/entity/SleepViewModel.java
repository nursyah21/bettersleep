package com.example.mobile.julfani.tubes.entity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SleepViewModel extends AndroidViewModel {
    private final SleepRepository sleepRepository;
    private final LiveData<List<Sleep>> allData;

    public SleepViewModel(@NonNull Application application) {
        super(application);
        sleepRepository = new SleepRepository(application);
        allData = sleepRepository.getAllData();
    }

    public LiveData<List<Sleep>> getAllData(){
        return allData;
    }

    public void insert(Sleep sleep){
        sleepRepository.insert(sleep);
    }

    public void deleteAll(){
        sleepRepository.deleteAll();
    }
}
