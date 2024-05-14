package com.example.busschedule.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM Schedule ORDER BY arrival_time ASC")
    fun getAll(): Flow<List<Schedule>>

    @Query("SELECT * FROM Schedule WHERE id = :id")
    fun getOneSchedule(id: Int): Flow<Schedule>

    @Query("SELECT * FROM Schedule WHERE stop_name = :stopName")
    fun getSchedulesFor(stopName: String): Flow<List<Schedule>>

    @Insert
    suspend fun insert(schedule: Schedule)

    @Update
    suspend fun update(schedule: Schedule)

    @Delete
    suspend fun delete(schedule: Schedule)
}
