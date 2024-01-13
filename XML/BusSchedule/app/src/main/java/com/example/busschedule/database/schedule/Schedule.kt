package com.example.busschedule.database.schedule

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@SuppressLint("KotlinNullnessAnnotation")
@Entity(tableName = "schedule")
data class Schedule(
    @NonNull @ColumnInfo(name = "stop_name") val stopName: String,
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "arrival_time") val arrivalTime: Int,
)
