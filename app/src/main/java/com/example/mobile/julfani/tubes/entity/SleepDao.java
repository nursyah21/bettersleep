package com.example.mobile.julfani.tubes.entity;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SleepDao {
    @Query("SELECT * FROM sleep_table ORDER BY date, time DESC")
    LiveData<List<Sleep>> getAllData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Sleep sleep);

    @Query("DELETE FROM sleep_table")
    void deleteAll();
}
