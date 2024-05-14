package com.example.busschedule.data

import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getOneScheduleAsStream(id: Int): Flow<Schedule>

    fun getScheduleFor(stopName: String): Flow<List<Schedule>>

    fun getAllSchedulesAsSteam(): Flow<List<Schedule>>

    suspend fun insert(schedule: Schedule)

    suspend fun delete(schedule: Schedule)

    suspend fun update(schedule: Schedule)

    class Local(
        private val dao: ScheduleDao,
    ) : ScheduleRepository {
        override fun getOneScheduleAsStream(id: Int): Flow<Schedule> = dao.getOneSchedule(id = id)

        override fun getScheduleFor(stopName: String): Flow<List<Schedule>> =
            dao.getSchedulesFor(stopName = stopName)

        override fun getAllSchedulesAsSteam(): Flow<List<Schedule>> = dao.getAll()

        override suspend fun insert(schedule: Schedule) = dao.insert(schedule)

        override suspend fun delete(schedule: Schedule) = dao.delete(schedule)

        override suspend fun update(schedule: Schedule) = dao.update(schedule)


    }
}
