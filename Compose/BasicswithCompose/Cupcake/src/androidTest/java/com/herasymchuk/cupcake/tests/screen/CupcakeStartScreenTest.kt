package com.herasymchuk.cupcake.tests.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.herasymchuk.cupcake.data.DataSource
import com.herasymchuk.cupcake.onNodeWithStringId
import com.herasymchuk.cupcake.ui.StartOrderScreen
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class CupcakeStartScreenTest {
    @get: Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun startScreen_VerifyContent() {
        val quantityOption = DataSource.quantityOptions
        var quantity = 0

        composeRule.setContent {
            StartOrderScreen(quantityOptions = quantityOption, onSelectQuantityButtonClicked = { quantity = it })
        }

        // All quantity options if displayed
        quantityOption.forEach {
            composeRule.onNodeWithStringId(it.first).isDisplayed()
        }

        val randomQuantity: Pair<Int, Int> = quantityOption.random()
        composeRule.onNodeWithStringId(randomQuantity.first).performClick()
        Assert.assertEquals(quantity, randomQuantity.second)
    }
}
