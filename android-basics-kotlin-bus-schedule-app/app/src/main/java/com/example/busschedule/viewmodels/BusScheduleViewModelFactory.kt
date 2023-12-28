package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.database.schedule.ScheduleDao
import java.lang.IllegalArgumentException

class BusScheduleViewModelFactory(
    private val scheduleDao: ScheduleDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusScheduleViewModel.Base(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
