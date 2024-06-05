package com.example.flightsearch.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch.data.database.model.DatabaseAirport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportSearchDao {
    @Query("SELECT * FROM airport WHERE name LIKE '%' || :text || '%' OR iata_code like '%' || :text || '%'")
    fun searchAirports(text: String): Flow<List<DatabaseAirport>>

    @Query("SELECT * FROM AIRPORT")
    fun getAllAirports(): Flow<List<DatabaseAirport>>

    @Query("SELECT * FROM airport WHERE id = :id")
    suspend fun getAllAirportsById(id: Int): DatabaseAirport
}
