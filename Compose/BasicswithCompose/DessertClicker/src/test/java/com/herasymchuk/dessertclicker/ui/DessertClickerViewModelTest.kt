package com.herasymchuk.dessertclicker.ui

import org.junit.Assert
import org.junit.Test

class DessertClickerViewModelTest {
    private val vm: DessertClickerViewModel =
        DessertClickerViewModel.BaseFactory().create(DessertClickerViewModel::class.java)
    private val state: DessertClickerState = vm.state.value

    @Test
    fun dessertClickerViewModel_SellOneDessert_RevenueAndSoldCoundIncrease() {
        val price: Int = state.dessert.price
        vm.sellDessert()

        Assert.assertTrue(state.revenue == price)
        Assert.assertTrue(state.dessertsSold == 1)
    }


    @Test
    fun determineDessertToShow() {
    }

    @Test
    fun shareSoldDessertsInformation() {
    }
}
