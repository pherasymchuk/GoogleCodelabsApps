package com.example.flightsearch.ui.mapper

import com.example.flightsearch.data.database.model.DatabaseAirport
import com.example.flightsearch.data.database.model.DatabaseFlight
import com.example.flightsearch.data.database.model.FavoriteFlight
import com.example.flightsearch.data.repository.AirportsRepository
import com.example.flightsearch.ui.model.UiAirport
import com.example.flightsearch.ui.model.UiFlight
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun DatabaseFlight.toUiModel(repo: AirportsRepository): UiFlight = coroutineScope {
    val departureAirport = async { repo.getAirportById(departureAirportId) }
    val arrivalAirport = async { repo.getAirportById(arrivalAirportId) }
    return@coroutineScope UiFlight(
        id = id,
        departureAirport = departureAirport.await().toUiModel(),
        arrivalAirport = arrivalAirport.await().toUiModel(),
        isFavorite = this@toUiModel.isFavorite == 1
    )
}

fun DatabaseAirport.toUiModel(): UiAirport = UiAirport(id = id, name = name, iataCode = iataCode)

fun UiFlight.toFavoriteModel(): FavoriteFlight {
    return FavoriteFlight(id = id, departureCode = departureAirport.iataCode, destinationCode = arrivalAirport.iataCode)
}
