package com.example.flightsearch.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.AppContainerProvider
import com.example.flightsearch.data.database.model.Airport
import com.example.flightsearch.data.database.model.Flight
import com.example.flightsearch.data.di.AppContainer
import com.example.flightsearch.data.repository.AirportsRepository
import com.example.flightsearch.data.repository.FlightsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class DetailsViewModel : ViewModel() {
    abstract val uiState: StateFlow<DetailsUiState>

    abstract fun fetchFlights(airportId: Int)

    data class DetailsUiState(
        val selectedAirport: Airport,
        val arrivalAirports: List<Airport>,
    )

    class Default(
        private val flightsRepository: FlightsRepository,
        private val airportsRepository: AirportsRepository,
        airport: Airport,
    ) : DetailsViewModel() {
        override val uiState: MutableStateFlow<DetailsUiState> = MutableStateFlow(
            DetailsUiState(airport, emptyList())
        )

        override fun fetchFlights(airportId: Int) {
            viewModelScope.launch {
                flightsRepository.getFlightsForAirport(airportId).collect { newFlights: List<Flight> ->
                    val arrivals = newFlights.map {
                        airportsRepository.getAirportById(id = it.arrivalAirportId)
                    }
                    uiState.update { it.copy(arrivalAirports = arrivals) }
                }
            }
        }
    }

    companion object {
        fun provideFactory(airport: Airport): ViewModelProvider.Factory = viewModelFactory {
            addInitializer(Default::class) {
                val container: AppContainer = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AppContainerProvider).appContainer
                Default(
                    flightsRepository = container.flightsRepository,
                    airportsRepository = container.airportsRepository,
                    airport = airport
                )
            }
        }
    }
}
