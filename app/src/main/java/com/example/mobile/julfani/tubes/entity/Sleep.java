package com.example.mobile.julfani.tubes.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mobile.julfani.tubes.utils.Utils;

@Entity(tableName = "sleep_table")
public class Sleep {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

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
        this.id = date + " " + time;
    }


    @NonNull
    public String getData() {
        return id + " " + String.valueOf(duration);
    }

    @NonNull
    public String getDate() {return date;}

    @NonNull
    public String getTime() {return time;}

    @NonNull
    public Integer getDuration() {return duration;}

    @NonNull
    public String getId() { return id;}

    public void setId(@NonNull String id){ this.id = id; }
}

