package com.example.flightsearch.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSearchDao {
    @Query("SELECT * FROM airport WHERE name = :name")
    fun searchFlights(name: String): Flow<List<Airport>>

    @Query("SELECT * FROM AIRPORT")
    fun getAllFlights(): Flow<List<Airport>>
}
