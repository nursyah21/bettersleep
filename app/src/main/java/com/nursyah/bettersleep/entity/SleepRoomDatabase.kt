package com.nursyah.bettersleep.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sleep::class], version = 1, exportSchema = false)
abstract class SleepRoomDatabase : RoomDatabase() {
    abstract fun sleepDao(): SleepDao?

    companion object {
        @Volatile
        private var INSTANCE: SleepRoomDatabase? = null

        fun getDatabase(context: Context): SleepRoomDatabase? {
            return INSTANCE?: synchronized(this) {
                        val instance = Room.databaseBuilder(context.applicationContext, SleepRoomDatabase::class.java, "sleep_database").build()
                        INSTANCE = instance
                        instance
            }
        }
    }
}