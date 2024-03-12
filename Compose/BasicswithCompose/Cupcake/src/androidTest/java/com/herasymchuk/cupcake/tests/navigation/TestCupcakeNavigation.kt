package com.herasymchuk.cupcake.tests.navigation

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.herasymchuk.cupcake.R
import com.herasymchuk.cupcake.data.DataSource
import com.herasymchuk.cupcake.onNodeWithStringId
import com.herasymchuk.cupcake.ui.CupcakeScreen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TestCupcakeNavigation(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) {
    private var currentScreen = CupcakeScreen.START

    fun navigateToStartScreen() {
        val repeat: Int = currentScreen.ordinal
        repeat(repeat) {
            clickNavigateUp()
        }
        this.currentScreen = CupcakeScreen.START
    }

    fun navigateToFlavorScreen(@StringRes flavorBtnId: Int = R.string.one_cupcake) {
        composeTestRule.onNodeWithStringId(flavorBtnId).performClick()
        currentScreen = CupcakeScreen.FLAVOR
    }

    fun navigateToPickupScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(DataSource.flavors.random()).performClick()
        clickNext()

        currentScreen = CupcakeScreen.PICKUP
    }

    fun navigateToSummaryScreen() {
        navigateToPickupScreen()

        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 3 dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        // choose random available date
        composeTestRule.onNodeWithText(dateOptions.random()).performClick()
        clickNext()
        currentScreen = CupcakeScreen.SUMMARY
    }

    fun clickNavigateUp() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun clickNext() {
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    fun clickCancel() {
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
    }
}
