package com.github.lunchtray.model

import java.text.NumberFormat

class FormattedPrice(price: Double) {
    val value: String = NumberFormat.getCurrencyInstance().format(price)

    override fun toString(): String = value
}
