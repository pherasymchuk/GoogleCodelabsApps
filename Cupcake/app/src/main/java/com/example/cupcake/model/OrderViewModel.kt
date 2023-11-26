package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class OrderViewModel : ViewModel() {
    abstract val quantity: LiveData<Int>
    abstract val flavor: LiveData<String>
    abstract val date: LiveData<String>
    abstract val price: LiveData<Double>

    abstract fun setQuantity(numberCupcakes: Int)
    abstract fun setFlavor(desiredFlavor: String)
    abstract fun setDate(pickupDate: String)

    class Base : OrderViewModel() {
        override var quantity = MutableLiveData(0)
        override var flavor = MutableLiveData("")
        override var date = MutableLiveData("")
        override var price = MutableLiveData(0.0)

        override fun setQuantity(numberCupcakes: Int) {
            quantity.value = numberCupcakes
        }

        override fun setFlavor(desiredFlavor: String) {
            flavor.value = desiredFlavor
        }

        override fun setDate(pickupDate: String) {
            date.value = pickupDate
        }
    }
}