package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.dao.FavoritesDao
import com.example.flightsearch.data.database.dao.FlightSearchDao
import com.example.flightsearch.data.database.model.DatabaseFlight
import com.example.flightsearch.data.database.model.FavoriteFlight
import com.example.flightsearch.ui.model.UiAirport
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {
    fun getFlightsForAirport(airportId: Int): Flow<List<DatabaseFlight>>
    suspend fun saveFlightToFavorites(flight: FavoriteFlight)
    suspend fun findFavoriteFlight(depart: UiAirport, arrival: UiAirport): List<FavoriteFlight>

    class Default(
        private val flightSearchDao: FlightSearchDao,
        private val favoritesDao: FavoritesDao,
    ) : FlightsRepository {

        override fun getFlightsForAirport(airportId: Int): Flow<List<DatabaseFlight>> =
            flightSearchDao.getFlightsForAirport(airportId)

        override suspend fun saveFlightToFavorites(flight: FavoriteFlight) {
            favoritesDao.saveFlightToFavorites(flight)
        }

        override suspend fun findFavoriteFlight(depart: UiAirport, arrival: UiAirport): List<FavoriteFlight> {
            return favoritesDao.findFavoriteFlight(depart.iataCode, arrival.iataCode)
        }

//        override suspend fun generateRandomFlights() {
//            flightSearchDao.clearFlights()
//            val airports = airportSearchDao.getAllAirports().first()
//            val flights = mutableListOf<Flight>()
//
//            airports.forEach { departureAirport ->
//                val potentialDestinations = airports
//                    .filter { it.id != departureAirport.id }
//                    .shuffled()
//                    .take(5)
//
//                potentialDestinations.forEach { arrivalAirport ->
//                    flights.add(
//                        Flight(
//                            departureAirportId = departureAirport.id,
//                            arrivalAirportId = arrivalAirport.id
//                        )
//                    )
//                }
//            }
//
//            flightSearchDao.insertAll(flights)
//        }
    }
}
