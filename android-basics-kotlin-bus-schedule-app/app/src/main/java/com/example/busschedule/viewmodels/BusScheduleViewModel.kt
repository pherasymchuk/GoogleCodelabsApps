package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao

abstract class BusScheduleViewModel : ViewModel() {

    abstract fun fullSchedule(): List<Schedule>
    abstract fun scheduleForStopName(name: String): List<Schedule>

    class Base(private val scheduleDao: ScheduleDao) : BusScheduleViewModel() {

        override fun fullSchedule(): List<Schedule> = scheduleDao.getAll()

        override fun scheduleForStopName(name: String): List<Schedule> =
            scheduleDao.getStopByName(name)
    }
}
