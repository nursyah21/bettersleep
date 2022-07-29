@file:Suppress("DEPRECATION")

package com.nursyah.bettersleep.entity

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class SleepRepository internal constructor(application: Application?) {
    private val sleepDao: SleepDao
    private val allData: LiveData<List<Sleep>>

    fun getAllData(): LiveData<List<Sleep>>{ return allData }

    fun insert(sleep: Sleep?) { InsertAsyncTask(sleepDao).execute(sleep) }

    fun deleteAll() { DeleteAsyncTask(sleepDao).execute() }


    private open class InsertAsyncTask(private val asyncSleepDao: SleepDao) : AsyncTask<Sleep?, Void?, Void?>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Sleep?): Void? {
            asyncSleepDao.insert(params[0])
            return null
        }
    }

    private open class DeleteAsyncTask(private val asyncSleepDao: SleepDao) : AsyncTask<Sleep?, Void?, Void?>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Sleep?): Void? {
            asyncSleepDao.deleteAll()
            return null
        }
    }

    init {
        val db = application?.let { SleepRoomDatabase.getDatabase(it) }
        sleepDao = db!!.sleepDao()!!
        allData = sleepDao.allData
    }
}