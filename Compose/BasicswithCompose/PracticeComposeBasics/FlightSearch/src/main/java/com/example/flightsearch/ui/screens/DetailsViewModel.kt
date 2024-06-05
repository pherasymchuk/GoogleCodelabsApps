package com.example.flightsearch.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.AppContainerProvider
import com.example.flightsearch.data.database.model.DatabaseFlight
import com.example.flightsearch.data.di.AppContainer
import com.example.flightsearch.data.repository.AirportsRepository
import com.example.flightsearch.data.repository.FlightsRepository
import com.example.flightsearch.ui.mapper.toFavoriteModel
import com.example.flightsearch.ui.mapper.toUiModel
import com.example.flightsearch.ui.model.UiAirport
import com.example.flightsearch.ui.model.UiFlight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class DetailsViewModel : ViewModel() {
    abstract val uiState: StateFlow<DetailsUiState>
    abstract fun fetchFlights()
    abstract fun saveFlightToFavorites(flight: UiFlight)

    data class DetailsUiState(
        val flights: List<UiFlight>,
    )

    class Default(
        private val flightsRepository: FlightsRepository,
        private val airportsRepository: AirportsRepository,
        private val airport: UiAirport,
    ) : DetailsViewModel() {
        override val uiState: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState(emptyList()))

        init {
            fetchFlights()
        }

        override fun fetchFlights() {
            viewModelScope.launch {
                flightsRepository.getFlightsForAirport(airport.id).collect { newFlights: List<DatabaseFlight> ->
                    val newFlightsAsUi = newFlights.map {
                        val uiFlight = it.toUiModel(airportsRepository)
                        uiFlight.copy(isFavorite = checkIfFlightIsFavorite(uiFlight))
                    }
                    uiState.update { it.copy(flights = newFlightsAsUi) }
                }
            }
        }

        private suspend fun checkIfFlightIsFavorite(flight: UiFlight): Boolean =
            flightsRepository.findFavoriteFlight(flight.departureAirport, flight.arrivalAirport).isNotEmpty()

        override fun saveFlightToFavorites(flight: UiFlight) {
            viewModelScope.launch {
                flightsRepository.saveFlightToFavorites(flight.toFavoriteModel())
                fetchFlights()
            }
        }


    }

    companion object {
        fun provideFactory(airport: UiAirport): ViewModelProvider.Factory = viewModelFactory {
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
