package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.dao.FlightSearchDao
import com.example.flightsearch.data.database.model.Flight
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {
    fun getFlightsForAirport(airportId: Int): Flow<List<Flight>>

    class Default(
        private val flightSearchDao: FlightSearchDao,
    ) : FlightsRepository {

        override fun getFlightsForAirport(airportId: Int): Flow<List<Flight>> =
            flightSearchDao.getFlightsForAirport(airportId)

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
