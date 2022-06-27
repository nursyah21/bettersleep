package com.example.bettersleep.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sleep_table")
public class Sleep {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private final Integer id = 0;

    @NonNull
    @ColumnInfo(name = "date")
    private final String date;

    @NonNull
    @ColumnInfo(name = "time")
    private final String time;

    @NonNull
    @ColumnInfo(name = "duration")
    private final Integer duration;


    public Sleep(@NonNull String date, @NonNull String time, @NonNull Integer duration) {
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    @NonNull
    public Integer getId(){
        return this.id;
    }

    @NonNull
    public String getDate(){
        return this.date;
    }

    @NonNull
    public String getTime(){
        return this.time;
    }

    @NonNull
    public Integer getDuration(){return this.duration;}


}
