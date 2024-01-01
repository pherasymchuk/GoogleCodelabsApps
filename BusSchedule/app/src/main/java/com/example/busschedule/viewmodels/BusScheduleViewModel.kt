package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao
import kotlinx.coroutines.flow.Flow

abstract class BusScheduleViewModel : ViewModel() {

    abstract fun fullSchedule(): Flow<List<Schedule>>
    abstract fun scheduleForStopName(name: String): Flow<List<Schedule>>

    class Base(private val scheduleDao: ScheduleDao) : BusScheduleViewModel() {

        override fun fullSchedule(): Flow<List<Schedule>> = scheduleDao.getAll()

        override fun scheduleForStopName(name: String): Flow<List<Schedule>> =
            scheduleDao.getStopByName(name)
    }
}
