package com.github.lunchtray.navigation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.lunchtray.LunchTrayScreen
import com.github.lunchtray.R
import com.github.lunchtray.datasource.DataSource
import com.github.lunchtray.onNodeWithStringId

class LunchTrayNavigation(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) {
    private var currentScreen: LunchTrayScreen = LunchTrayScreen.START

    fun toEntryMenuScreen() {
        composeTestRule.onNodeWithStringId(R.string.start_order, true).performClick()
        currentScreen = LunchTrayScreen.ENTRY_MENU
    }

    fun returnToStartScreen() {
        repeat(currentScreen.ordinal) {
            clickBack()
        }
    }

    fun toSideDishMenuScreen() {
        toEntryMenuScreen()
        composeTestRule.onNodeWithText(DataSource.entreeMenuItems.random().name).performClick()
        clickNext()
        currentScreen = LunchTrayScreen.SIDE_DISH_MENU
    }

    fun toAccompanimentMenuScreen() {
        toSideDishMenuScreen()
        composeTestRule.onNodeWithText(DataSource.sideDishMenuItems.random().name).performClick()
        clickNext()
        currentScreen = LunchTrayScreen.ACCOMPANIMENT_MENU
    }

    fun toCheckoutScreen() {
        toAccompanimentMenuScreen()
        composeTestRule.onNodeWithText(DataSource.accompanimentMenuItems.random().name).performClick()
        clickNext()
        currentScreen = LunchTrayScreen.CHECKOUT
    }

    fun clickNext() {
        composeTestRule.onNodeWithStringId(R.string.next, ignoreCase = true)
    }

    fun clickBack() {
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back_button))
            .performClick()
    }

    fun cancelOrder() {
        composeTestRule.onNodeWithStringId(R.string.cancel, ignoreCase = true).performClick()
        currentScreen = LunchTrayScreen.START
    }
}
