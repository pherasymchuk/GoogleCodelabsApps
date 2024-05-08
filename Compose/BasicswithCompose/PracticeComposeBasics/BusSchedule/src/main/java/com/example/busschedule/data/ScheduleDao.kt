package com.example.busschedule.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM BusSchedule ORDER BY arrivalTimeInMillis DESC")
    fun getAllSchedules(): Flow<List<BusSchedule>>

    @Query("SELECT * FROM BUSSCHEDULE WHERE id = :id")
    fun getOneSchedule(id: Int): Flow<BusSchedule>

    @Insert
    fun insert(schedule: BusSchedule)

    @Update
    fun update(schedule: BusSchedule)

    @Delete
    fun delete(schedule: BusSchedule)
}
