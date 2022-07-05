package com.example.mobile.julfani.tubes.entity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SleepRepository {
    private final SleepDao sleepDao;
    private final LiveData<List<Sleep>> allData;

    SleepRepository(Application application){
        SleepRoomDatabase db = SleepRoomDatabase.getDatabase(application);
        sleepDao = db.sleepDao();
        allData = sleepDao.getAllData();
    }

    public LiveData<List<Sleep>> getAllData(){
        return allData;
    }

    /* insert */
    public void insert(Sleep sleep){
        new insertAsyncTask(sleepDao).execute(sleep);
    }

    /* deleteAll */
    public void deleteAll(){
        new deleteAllAsyncTask(sleepDao).execute();
    }


    @SuppressWarnings("deprecation")
    private static class insertAsyncTask extends AsyncTask<Sleep, Void, Void>{
        private final SleepDao asyncSleepDao;

        private insertAsyncTask(SleepDao asyncSleepDao) {
            this.asyncSleepDao = asyncSleepDao;
        }

        @Override
        protected Void doInBackground(Sleep... sleeps) {
            asyncSleepDao.insert(sleeps[0]);
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private static class deleteAllAsyncTask extends AsyncTask<Sleep, Void, Void>{
        private final SleepDao sleepDao;

        private deleteAllAsyncTask(SleepDao sleepDao){
            this.sleepDao = sleepDao;
        }

        @Override
        protected Void doInBackground(Sleep... sleeps) {
            sleepDao.deleteAll();
            return null;
        }
    }
}
