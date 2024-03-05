package com.herasymchuk.dessertclicker.ui

import com.herasymchuk.dessertclicker.model.Dessert

data class DessertClickerState(
    val revenue: Int,
    val dessertsSold: Int,
    val dessert: Dessert,
)
