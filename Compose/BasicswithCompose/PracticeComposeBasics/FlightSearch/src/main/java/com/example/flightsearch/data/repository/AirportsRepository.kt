package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.dao.AirportSearchDao
import com.example.flightsearch.data.database.model.Airport
import kotlinx.coroutines.flow.Flow

interface AirportsRepository {
    fun getAllAirports(): Flow<List<Airport>>
    fun searchAirports(text: String): Flow<List<Airport>>
    suspend fun getAirportById(id: Int): Airport

    class Default(private val dao: AirportSearchDao) : AirportsRepository {
        override fun getAllAirports(): Flow<List<Airport>> = dao.getAllAirports()

        override fun searchAirports(text: String): Flow<List<Airport>> = dao.searchAirports(text)

        override suspend fun getAirportById(id: Int): Airport = dao.getAllAirportsById(id)
    }
}
