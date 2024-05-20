package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.database.FlightSearchDao
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {
    fun getFlights(name: String): Flow<List<Airport>>

    class Default(private val dao: FlightSearchDao) : FlightsRepository {
        override fun getFlights(name: String): Flow<List<Airport>> {
            return dao.getFlights(name)
        }
    }
}
