package com.example.flightsearch.ui.model

data class UiFlight(
    val id: Int,
    val departureAirport: UiAirport,
    val arrivalAirport: UiAirport,
    val isFavorite: Boolean,
)
