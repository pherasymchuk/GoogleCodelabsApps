package com.example.flightsearch.ui

import com.example.flightsearch.data.database.model.FavoriteFlight
import com.example.flightsearch.data.repository.FlightsRepository
import com.example.flightsearch.ui.mapper.toFavoriteModel
import com.example.flightsearch.ui.model.UiFlight
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface FavoritesManager {
    suspend fun checkIfFlightIsFavorite(id: Int): Boolean
    suspend fun saveOrRemoveFlightFromFavorites(flight: UiFlight)
    suspend fun getAllFavoriteFlights()

    val favoriteFlights: StateFlow<List<FavoriteFlight>>

    class Default(
        private val flightsRepository: FlightsRepository,
    ) : FavoritesManager {

        override val favoriteFlights: MutableStateFlow<List<FavoriteFlight>> = MutableStateFlow(emptyList())

        override suspend fun getAllFavoriteFlights(): Unit = coroutineScope {
            launch {
                flightsRepository.getAllFavoriteFlights()
                    .stateIn(
                        scope = this,
                        started = SharingStarted.WhileSubscribed(5000),
                        initialValue = emptyList()
                    )
                    .collect {
                        favoriteFlights.value = it
                    }
            }
        }

        override suspend fun checkIfFlightIsFavorite(id: Int): Boolean {
            return favoriteFlights.value.any { it.id == id }
        }

        override suspend fun saveOrRemoveFlightFromFavorites(flight: UiFlight) {
            if (flight.isFavorite) {
                flightsRepository.removeFlightFromFavorites(flight.toFavoriteModel())
            } else {
                flightsRepository.saveFlightToFavorites(flight.toFavoriteModel())
            }
        }
    }
}
