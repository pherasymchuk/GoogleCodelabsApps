package com.example.flightsearch.data.database

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSearchDao {
    @androidx.room.Query("SELECT * FROM airport WHERE name = :name")
    fun getFlights(name: String): Flow<List<Airport>>
}
