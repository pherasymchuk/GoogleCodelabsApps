package com.example.flightsearch.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch.data.database.model.DatabaseFlight
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSearchDao {
    @Query("SELECT * FROM flight_search WHERE departure_airport_id = :airportId")
    fun getFlightsForAirport(airportId: Int): Flow<List<DatabaseFlight>>
}
