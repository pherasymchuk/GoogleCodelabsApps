package com.example.busschedule.data

import android.content.Context

interface AppContainer {
    val scheduleRepository: ScheduleRepository

    class Default(private val context: Context) : AppContainer {
        override val scheduleRepository: ScheduleRepository by lazy {
            ScheduleRepository.Local(ScheduleDatabase.getDatabase(context).getScheduleDao())
        }
    }
}
