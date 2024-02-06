package com.example.tiptime

import java.text.NumberFormat

abstract class Tip(amount: Double, percent: Double) {
    protected open val tip = percent / 100 * amount

    abstract fun value(): String

    class Base(amount: Double, percent: Double = 15.0) : Tip(amount, percent) {

        override fun value(): String {
            return NumberFormat.getCurrencyInstance().format(tip)
        }
    }

    class RoundUp(amount: Double, percent: Double) : Tip(amount, percent) {
        override val tip = kotlin.math.ceil(super.tip)

        override fun value(): String {
            return NumberFormat.getCurrencyInstance().format(tip)
        }

    }
}
