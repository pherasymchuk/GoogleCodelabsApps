package com.example.flightsearch.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.AppContainerProvider
import com.example.flightsearch.data.database.model.DatabaseFlight
import com.example.flightsearch.data.database.model.FavoriteFlight
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
    abstract fun saveOrRemoveFlightFromFavorites(flight: UiFlight)

    data class DetailsUiState(
        val flights: List<UiFlight>,
    )

    class Default(
        private val flightsRepository: FlightsRepository,
        private val airportsRepository: AirportsRepository,
        private val airport: UiAirport,
    ) : DetailsViewModel() {
        override val uiState: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState(emptyList()))
        private var favoriteFlights: List<FavoriteFlight> = emptyList()

        init {
            fetchFlights()
        }

        override fun fetchFlights() {
            viewModelScope.launch {
                fetchAllFavoriteFlights()
                flightsRepository.getFlightsForAirport(airport.id).collect { newFlights: List<DatabaseFlight> ->
                    val newFlightsAsUi: List<UiFlight> = newFlights.map { databaseFlight ->
                        val uiFlight: UiFlight = databaseFlight.toUiModel(airportsRepository)
                        uiFlight.copy(isFavorite = checkIfFlightIsFavorite(uiFlight.id))
                    }
                    uiState.update { it.copy(flights = newFlightsAsUi) }
                }
            }
        }

        private suspend fun fetchAllFavoriteFlights() {
            viewModelScope.launch {
                flightsRepository.getAllFavoriteFlights().collect {
                    favoriteFlights = it
                }
            }
        }

        private fun checkIfFlightIsFavorite(id: Int): Boolean =
            favoriteFlights.any { it.id == id }

        override fun saveOrRemoveFlightFromFavorites(flight: UiFlight) {
            if (flight.isFavorite) {
                viewModelScope.launch {
                    flightsRepository.removeFlightFromFavorites(flight.toFavoriteModel())
                }
            } else {
                viewModelScope.launch {
                    flightsRepository.saveFlightToFavorites(flight.toFavoriteModel())
                    fetchFlights()
                }
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
