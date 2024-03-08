/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.herasymchuk.cupcake.ui

import androidx.lifecycle.ViewModel
import com.herasymchuk.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Price for a single cupcake */
private const val PRICE_PER_CUPCAKE = 2.00

/** Additional cost for same day pickup of an order */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/**
 * [OrderViewModel] holds information about a cupcake order in terms of quantity, flavor, and
 * pickup date. It also knows how to calculate the total price based on these order details.
 */
abstract class OrderViewModel : ViewModel() {

    /**
     * Cupcake state for this order
     */
    abstract val uiState: StateFlow<OrderUiState>

    /**
     * Set the quantity [numberCupcakes] of cupcakes for this order's state and update the price
     */
    abstract fun setQuantity(numberCupcakes: Int)

    /**
     * Set the [desiredFlavor] of cupcakes for this order's state.
     * Only 1 flavor can be selected for the whole order.
     */
    abstract fun setFlavor(desiredFlavor: String)

    /**
     * Set the [pickupDate] for this order's state and update the price
     */
    abstract fun setDate(pickupDate: String)

    /**
     * Reset the order state
     */
    abstract fun resetOrder()


    class Base : OrderViewModel() {

        override val uiState: MutableStateFlow<OrderUiState> =
            MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))

        override fun setQuantity(numberCupcakes: Int) {
            uiState.update { currentState ->
                currentState.copy(
                    quantity = numberCupcakes,
                    price = calculatePrice(quantity = numberCupcakes)
                )
            }
        }

        override fun setFlavor(desiredFlavor: String) {
            uiState.update { currentState ->
                currentState.copy(flavor = desiredFlavor)
            }
        }

        override fun setDate(pickupDate: String) {
            uiState.update { currentState ->
                currentState.copy(
                    date = pickupDate,
                    price = calculatePrice(pickupDate = pickupDate)
                )
            }
        }

        override fun resetOrder() {
            uiState.value = OrderUiState(pickupOptions = pickupOptions())
        }

        /**
         * Returns the calculated price based on the order details.
         */
        private fun calculatePrice(
            quantity: Int = uiState.value.quantity,
            pickupDate: String = uiState.value.date,
        ): String {
            var calculatedPrice = quantity * PRICE_PER_CUPCAKE
            // If the user selected the first option (today) for pickup, add the surcharge
            if (pickupOptions()[0] == pickupDate) {
                calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
            }
            val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
            return formattedPrice
        }

        /**
         * Returns a list of date options starting with the current date and the following 3 dates.
         */
        private fun pickupOptions(): List<String> {
            val dateOptions = mutableListOf<String>()
            val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
            val calendar = Calendar.getInstance()
            // add current date and the following 3 dates.
            repeat(4) {
                dateOptions.add(formatter.format(calendar.time))
                calendar.add(Calendar.DATE, 1)
            }
            return dateOptions
        }
    }
}
