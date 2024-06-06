package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.dao.FavoritesDao
import com.example.flightsearch.data.database.dao.FlightSearchDao
import com.example.flightsearch.data.database.model.DatabaseFlight
import com.example.flightsearch.data.database.model.FavoriteFlight
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {
    fun getFlightsForAirport(airportId: Int): Flow<List<DatabaseFlight>>
    fun getAllFavoriteFlights(): Flow<List<FavoriteFlight>>
    suspend fun saveFlightToFavorites(flight: FavoriteFlight)
    suspend fun removeFlightFromFavorites(flight: FavoriteFlight)
    suspend fun findFavoriteFlight(id: Int): List<FavoriteFlight>

    class Default(
        private val flightSearchDao: FlightSearchDao,
        private val favoritesDao: FavoritesDao,
    ) : FlightsRepository {

        override fun getFlightsForAirport(airportId: Int): Flow<List<DatabaseFlight>> =
            flightSearchDao.getFlightsForAirport(airportId)

        override fun getAllFavoriteFlights(): Flow<List<FavoriteFlight>> = favoritesDao.getAllFavoriteFlights()

        override suspend fun saveFlightToFavorites(flight: FavoriteFlight) {
            favoritesDao.saveFlightToFavorites(flight)
        }

        override suspend fun removeFlightFromFavorites(flight: FavoriteFlight) {
            favoritesDao.removeFlightFromFavorites(flight)
        }

        override suspend fun findFavoriteFlight(id: Int): List<FavoriteFlight> = favoritesDao.findFavoriteFlight(id)
    }
}
