package com.example.tiptime

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.text.NumberFormat

class TipTests {
    @Test
    fun `calculate tip 20 percent no roundup`() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip: String = NumberFormat.getCurrencyInstance().format(2)
        val result: String = Tip.Base(amount, tipPercent).value()
        Assertions.assertEquals(expectedTip, result)
    }

    @Test
    fun `calculate tip 33 percent roundup`() {
        val amount = 133.00
        val tipPercent = 33.00
        val expectedTip: String = NumberFormat.getCurrencyInstance().format(44)
        val result: String = Tip.RoundUp(amount, tipPercent).value()
        Assertions.assertEquals(expectedTip, result)
    }
}
