package com.example.flightsearch.data.di

import android.content.Context
import com.example.flightsearch.data.database.FlightSearchDatabase
import com.example.flightsearch.data.repository.FlightsRepository

interface AppContainer {
    val repository: FlightsRepository

    class Default(private val context: Context) : AppContainer {
        override val repository: FlightsRepository =
            FlightsRepository.Default(FlightSearchDatabase.getInstance(context).flightSearchDao())
    }
}
