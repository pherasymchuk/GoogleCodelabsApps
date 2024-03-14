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
package com.github.lunchtray.ui

import androidx.lifecycle.ViewModel
import com.github.lunchtray.model.MenuItem
import com.github.lunchtray.model.MenuItem.AccompanimentItem
import com.github.lunchtray.model.MenuItem.EntreeItem
import com.github.lunchtray.model.MenuItem.SideDishItem
import com.github.lunchtray.model.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class OrderViewModel : ViewModel() {
    abstract val uiState: StateFlow<OrderUiState>

    abstract fun updateEntree(entree: EntreeItem)
    abstract fun updateSideDish(sideDish: SideDishItem)
    abstract fun updateAccompaniment(accompaniment: AccompanimentItem)
    abstract fun resetOrder()

    class Base : OrderViewModel() {
        private val taxRate = 0.08
        override val uiState = MutableStateFlow(OrderUiState())

        override fun updateEntree(entree: EntreeItem) {
            val previousEntree = uiState.value.entree
            updateItem(entree, previousEntree)
        }

        override fun updateSideDish(sideDish: SideDishItem) {
            val previousSideDish = uiState.value.sideDish
            updateItem(sideDish, previousSideDish)
        }

        override fun updateAccompaniment(accompaniment: AccompanimentItem) {
            val previousAccompaniment = uiState.value.accompaniment
            updateItem(accompaniment, previousAccompaniment)
        }

        override fun resetOrder() {
            uiState.value = OrderUiState()
        }

        private fun updateItem(newItem: MenuItem, previousItem: MenuItem?) {
            uiState.update { currentState ->
                val previousItemPrice = previousItem?.price ?: 0.0
                // subtract previous item price in case an item of this category was already added.
                val itemTotalPrice = currentState.itemTotalPrice - previousItemPrice + newItem.price
                // recalculate tax
                val tax = itemTotalPrice * taxRate
                currentState.copy(
                    itemTotalPrice = itemTotalPrice,
                    orderTax = tax,
                    orderTotalPrice = itemTotalPrice + tax,
                    entree = if (newItem is EntreeItem) newItem else currentState.entree,
                    sideDish = if (newItem is SideDishItem) newItem else currentState.sideDish,
                    accompaniment =
                    if (newItem is AccompanimentItem) newItem else currentState.accompaniment
                )
            }
        }
    }
}

