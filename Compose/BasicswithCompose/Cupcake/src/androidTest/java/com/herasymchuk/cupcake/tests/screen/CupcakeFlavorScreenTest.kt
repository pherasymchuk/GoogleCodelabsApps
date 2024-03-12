package com.herasymchuk.cupcake.tests.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.herasymchuk.cupcake.R
import com.herasymchuk.cupcake.onNodeWithStringId
import com.herasymchuk.cupcake.ui.SelectOptionScreen
import org.junit.Rule
import org.junit.Test

class CupcakeFlavorScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_VerifyContent() {
        // Given list of options
        val flavors: List<String> = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        // All options are displayed on the screen
        flavors.forEach {
            composeTestRule.onNodeWithText(it).assertIsDisplayed()
        }

        // Subtotal is displayed correctly
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, subtotal)
        ).assertIsDisplayed()

        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }
}
