package com.example.flightsearch.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class DatabaseAirport(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "iata_code") val iataCode: String,
    val passengers: Int,
)
