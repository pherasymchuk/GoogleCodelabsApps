package com.example.flightsearch.ui

import com.example.flightsearch.data.database.model.FavoriteFlight
import com.example.flightsearch.data.repository.FlightsRepository
import com.example.flightsearch.ui.mapper.toFavoriteModel
import com.example.flightsearch.ui.model.UiFlight

interface FavoritesManager {
    suspend fun checkIfFlightIsFavorite(id: Int): Boolean
    suspend fun saveOrRemoveFlightFromFavorites(flight: UiFlight)
    suspend fun fetchAllFavoriteFlights()


    class Default(
        private val flightsRepository: FlightsRepository,
    ) : FavoritesManager {

        private var favoriteFlights: List<FavoriteFlight> = emptyList()

        override fun fetchAllFavoriteFlights() {
            flightsRepository.getAllFavoriteFlights().collect {
                favoriteFlights = it
            }
        }

        override fun checkIfFlightIsFavorite(id: Int): Boolean =
            favoriteFlights.any { it.id == id }

        override fun saveOrRemoveFlightFromFavorites(flight: UiFlight) {
            if (flight.isFavorite) {
                flightsRepository.removeFlightFromFavorites(flight.toFavoriteModel())
            } else {
                flightsRepository.saveFlightToFavorites(flight.toFavoriteModel())
            }
        }
    }
}
