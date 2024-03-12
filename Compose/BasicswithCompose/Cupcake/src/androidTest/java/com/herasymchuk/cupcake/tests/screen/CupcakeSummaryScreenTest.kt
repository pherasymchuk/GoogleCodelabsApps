package com.herasymchuk.cupcake.tests.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.herasymchuk.cupcake.R
import com.herasymchuk.cupcake.data.OrderUiState
import com.herasymchuk.cupcake.ui.OrderSummaryScreen
import org.junit.Rule
import org.junit.Test

class CupcakeSummaryScreenTest {
    @get: Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun summaryScreen_VerifyContent() {
        val quantity = 6
        val flavor = "Vanilla"
        val date = "Tomorrow"
        val price = "$24"

        val uiState = OrderUiState(
            quantity = quantity,
            flavor = flavor,
            date = date,
            price = price
        )

        composeRule.setContent {
            OrderSummaryScreen(
                orderUiState = uiState,
                onCancelButtonClicked = { },
                onSendButtonClicked = { _, _ -> }
            )
        }

        composeRule.onNodeWithText(
            composeRule.activity.resources.getQuantityString(
                R.plurals.cupcakes,
                quantity,
                quantity
            )
        ).assertIsDisplayed()

        composeRule.onNodeWithText(flavor).assertIsDisplayed()
        composeRule.onNodeWithText(date).assertIsDisplayed()
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.subtotal_price, price))
            .assertIsDisplayed()
    }
}
