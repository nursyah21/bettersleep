package com.example.mobile.julfani.tubes.entity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Sleep.class}, version = 1, exportSchema = false)
public abstract class SleepRoomDatabase extends RoomDatabase {
    public abstract SleepDao sleepDao();
    private static volatile SleepRoomDatabase INSTANCE;

    static SleepRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (SleepRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SleepRoomDatabase.class, "sleep_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
