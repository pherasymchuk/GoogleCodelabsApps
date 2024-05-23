package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.database.FlightSearchDao
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {
    fun getAllFlights(): Flow<List<Airport>>
    fun searchFlights(text: String): Flow<List<Airport>>

    class Default(private val dao: FlightSearchDao) : FlightsRepository {
        override fun getAllFlights(): Flow<List<Airport>> = dao.getAllFlights()

        override fun searchFlights(text: String): Flow<List<Airport>> = dao.searchFlights(text)
    }
}
