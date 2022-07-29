package com.nursyah.bettersleep.entity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class SleepViewModel(application: Application) : AndroidViewModel(application) {
    private val sleepRepository: SleepRepository
    private val allData: LiveData<List<Sleep>>

    fun getAllData(): LiveData<List<Sleep>>{
        return allData
    }

    fun insert(sleep: Sleep?) {
        sleepRepository.insert(sleep)
    }

    fun deleteAll() {
        sleepRepository.deleteAll()
    }

    init {
        sleepRepository = SleepRepository(application)
        allData = sleepRepository.getAllData()
    }
}