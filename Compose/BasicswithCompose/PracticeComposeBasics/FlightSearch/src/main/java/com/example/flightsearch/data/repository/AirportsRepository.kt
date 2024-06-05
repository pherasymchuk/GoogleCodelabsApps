package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.dao.AirportSearchDao
import com.example.flightsearch.data.database.model.DatabaseAirport
import kotlinx.coroutines.flow.Flow

interface AirportsRepository {
    fun getAllAirports(): Flow<List<DatabaseAirport>>
    fun searchAirports(text: String): Flow<List<DatabaseAirport>>
    suspend fun getAirportById(id: Int): DatabaseAirport

    class Default(private val dao: AirportSearchDao) : AirportsRepository {
        override fun getAllAirports(): Flow<List<DatabaseAirport>> = dao.getAllAirports()

        override fun searchAirports(text: String): Flow<List<DatabaseAirport>> = dao.searchAirports(text)

        override suspend fun getAirportById(id: Int): DatabaseAirport = dao.getAllAirportsById(id)
    }
}
