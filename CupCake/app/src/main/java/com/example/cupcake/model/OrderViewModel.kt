package com.example.cupcake.model

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Locale

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/**
 * Never instantiate this class directly, use [OrderViewModel.Base] implementation
 */
abstract class OrderViewModel : ViewModel() {
    abstract val quantity: LiveData<Int>
    abstract val flavor: LiveData<String>
    abstract val date: LiveData<String>
    abstract val price: LiveData<Double>
    abstract val dateOptions: List<String>

    abstract fun setQuantity(numberCupcakes: Int)
    abstract fun setFlavor(desiredFlavor: String)
    abstract fun setDate(pickupDate: String)
    abstract fun getPickupOptions(): List<String>
    abstract fun resetOrder()

    class Base : OrderViewModel() {
        override var quantity = MutableLiveData<Int>()
        override var flavor = MutableLiveData<String>()
        override var date = MutableLiveData<String>()
        override var price = MutableLiveData<Double>()
        override val dateOptions: List<String> = getPickupOptions()

        init {
            resetOrder()
        }

        override fun setQuantity(numberCupcakes: Int) {
            quantity.value = numberCupcakes
            updatePrice()
        }

        override fun setFlavor(desiredFlavor: String) {
            flavor.value = desiredFlavor
        }

        override fun setDate(pickupDate: String) {
            date.value = pickupDate
            updatePrice()
        }

        override fun getPickupOptions(): List<String> {
            val options = mutableListOf<String>()
            val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
            val calendar = Calendar.getInstance()

            repeat(4) {
                options.add(formatter.format(calendar.time))
                calendar.add(Calendar.DATE, 1)
            }
            return options
        }

        override fun resetOrder() {
            quantity.value = 0
            flavor.value = ""
            date.value = dateOptions[0]
            price.value = 0.0
        }

        private fun updatePrice() {
            var calculatedPrice = (quantity.value ?: 0).times(PRICE_PER_CUPCAKE)
            if (dateOptions[0] == date.value) {
                calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
            }
            price.value = calculatedPrice
        }
    }
}
