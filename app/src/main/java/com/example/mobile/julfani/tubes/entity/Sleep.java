package com.example.mobile.julfani.tubes.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sleep_table")
public class Sleep {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "data")
    private final String data;

    public Sleep(@NonNull String data) {
        this.data = data;
    }

    @NonNull
    public String getData() {
        return data;
    }
}
