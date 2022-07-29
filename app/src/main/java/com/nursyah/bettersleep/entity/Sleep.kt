package com.nursyah.bettersleep.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "sleep_table")
data class Sleep(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true) val id:Long = 0,

    @field:ColumnInfo(name = "date") val date:String,

    @field:ColumnInfo(name = "duration") val duration:Int,
)