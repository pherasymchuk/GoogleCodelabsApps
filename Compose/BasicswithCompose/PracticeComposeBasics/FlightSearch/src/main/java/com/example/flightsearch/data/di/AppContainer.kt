package com.example.flightsearch.data.di

import android.content.Context
import com.example.flightsearch.data.database.FlightSearchDatabase
import com.example.flightsearch.data.repository.AirportsRepository
import com.example.flightsearch.data.repository.FlightsRepository

interface AppContainer {
    val airportsRepository: AirportsRepository
    val flightsRepository: FlightsRepository

    class Default(private val context: Context) : AppContainer {
        override val airportsRepository: AirportsRepository =
            AirportsRepository.Default(FlightSearchDatabase.getInstance(context).airportSearchDao())
        override val flightsRepository: FlightsRepository =
            FlightsRepository.Default(FlightSearchDatabase.getInstance(context).flightSearchDao())
    }
}
