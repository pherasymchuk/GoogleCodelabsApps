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
import com.example.flightsearch.ui.FavoritesManager
import com.example.flightsearch.ui.mapper.toUiModel
import com.example.flightsearch.ui.model.UiAirport
import com.example.flightsearch.ui.model.UiFlight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class DetailsViewModel : ViewModel() {
    abstract val uiState: StateFlow<DetailsUiState>

    abstract fun toggleFavorite(flight: UiFlight)
    protected abstract fun refreshFlights()

    data class DetailsUiState(
        val flights: List<UiFlight>,
    )

    class Default(
        private val flightsRepository: FlightsRepository,
        private val favoritesManager: FavoritesManager,
        private val airportsRepository: AirportsRepository,
        private val airport: UiAirport,
    ) : DetailsViewModel() {
        override val uiState: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState(emptyList()))

        init {
            refreshFlights()
            viewModelScope.launch {
                favoritesManager.getAllFavoriteFlights()
            }
        }


        override fun refreshFlights() {
            viewModelScope.launch {
                flightsRepository.getFlightsForAirport(airport.id).collect { newFlights: List<DatabaseFlight> ->
                    val newFlightsAsUi: List<UiFlight> = newFlights.map { databaseFlight ->
                        val uiFlight: UiFlight = databaseFlight.toUiModel(airportsRepository)
                        uiFlight.copy(isFavorite = favoritesManager.checkIfFlightIsFavorite(uiFlight.id))
                    }
                    uiState.update { it.copy(flights = newFlightsAsUi) }
                }
            }
        }

        override fun toggleFavorite(flight: UiFlight) {
            viewModelScope.launch {
                favoritesManager.toggleFlightFavoriteStatus(flight)
                refreshFlights()
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
                    airport = airport,
                    favoritesManager = FavoritesManager.Default(container.flightsRepository)
                )
            }
        }
    }
}
