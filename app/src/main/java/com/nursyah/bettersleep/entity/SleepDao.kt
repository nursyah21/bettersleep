package com.nursyah.bettersleep.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDao {
    @get:Query("SELECT * FROM sleep_table ORDER BY id DESC")
    val allData: LiveData<List<Sleep>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(sleep: Sleep?)

    @Query("DELETE FROM sleep_table")
    fun deleteAll()
}