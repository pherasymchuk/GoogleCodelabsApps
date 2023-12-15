/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lunchtray.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lunchtray.data.DataSource
import java.text.NumberFormat

abstract class OrderViewModel : ViewModel() {
    abstract val menuItems: Map<String, MenuItem>
    abstract val entree: LiveData<MenuItem?>
    abstract val side: LiveData<MenuItem?>
    abstract val accompaniment: LiveData<MenuItem?>
    abstract val subtotalFormatted: LiveData<String>
    abstract val totalFormatted: LiveData<String>
    abstract val taxFormatted: LiveData<String>

    abstract fun setEntree(entree: String)
    abstract fun setSide(side: String)
    abstract fun setAccompaniment(accompaniment: String)
    abstract fun calculateTaxAndTotal()
    abstract fun resetOrder()

    class Base : OrderViewModel() {
        override val menuItems = DataSource.menuItems

        private var previousEntreePrice = 0.0
        private var previousSidePrice = 0.0
        private var previousAccompanimentPrice = 0.0

        private val taxRate = 0.08

        override var entree = MutableLiveData<MenuItem?>()
        override val side = MutableLiveData<MenuItem?>()
        override val accompaniment = MutableLiveData<MenuItem?>()
        private val subtotal = MutableLiveData(0.0)
        override val subtotalFormatted: LiveData<String> = subtotal.map {
            NumberFormat.getCurrencyInstance().format(it)
        }
        private val total = MutableLiveData(0.0)
        override val totalFormatted: LiveData<String> = total.map {
            NumberFormat.getCurrencyInstance().format(it)
        }
        private val tax = MutableLiveData(0.0)
        override val taxFormatted: LiveData<String> = tax.map {
            NumberFormat.getCurrencyInstance().format(it)
        }

        /**
         * Set the entree for the order.
         */
        override fun setEntree(entree: String) {
            val selectedEntree: MenuItem? = menuItems[entree]
            if (selectedEntree != null) {
                previousEntreePrice = this.entree.value?.price ?: 0.0
                this.entree.value = selectedEntree
                subtotal.value =
                    (subtotal.value ?: 0.0) - previousEntreePrice + selectedEntree.price
            }
        }

        /**
         * Set the side for the order.
         */
        override fun setSide(side: String) {
            val selectedSide: MenuItem? = menuItems[side]
            if (selectedSide != null) {
                previousSidePrice = this.side.value?.price ?: 0.0
                this.side.value = selectedSide
                subtotal.value = (subtotal.value ?: 0.0) - previousSidePrice + selectedSide.price
            }
        }

        /**
         * Set the accompaniment for the order.
         */
        override fun setAccompaniment(accompaniment: String) {
            val selectedAccompaniment: MenuItem? = menuItems[accompaniment]
            if (selectedAccompaniment != null) {
                previousAccompanimentPrice = this.accompaniment.value?.price ?: 0.0
                this.accompaniment.value = selectedAccompaniment
                subtotal.value = (subtotal.value
                    ?: 0.0) - previousAccompanimentPrice + selectedAccompaniment.price
            }
        }

//        /**
//         * Update subtotal value.
//         */
//        private fun updateSubtotal(itemPrice: Double) {
//            // TODO: if _subtotal.value is not null, update it to reflect the price of the recently
//            //  added item.
//            //  Otherwise, set _subtotal.value to equal the price of the item.
//
//            // TODO: calculate the tax and resulting total
//            if (subtotal.value != null) {
//                subtotal.value = itemPrice
//            }
//        }

        /**
         * Calculate tax and update total.
         */
        override fun calculateTaxAndTotal() {
            tax.value = subtotal.value?.times(taxRate)
            total.value = subtotal.value?.plus(tax.value ?: 0.0)
        }

        /**
         * Reset all values pertaining to the order.
         */
        override fun resetOrder() {
            previousEntreePrice = 0.0
            previousSidePrice = 0.0
            previousAccompanimentPrice = 0.0
            entree.value = null
            side.value = null
            accompaniment.value = null
            subtotal.value = 0.0
            total.value = 0.0
            tax.value = 0.0
        }
    }
}
