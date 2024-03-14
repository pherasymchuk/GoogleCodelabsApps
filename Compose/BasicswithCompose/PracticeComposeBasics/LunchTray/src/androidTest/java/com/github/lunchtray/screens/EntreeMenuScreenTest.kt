package com.github.lunchtray.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.github.lunchtray.R
import com.github.lunchtray.chooseRandomEntry
import com.github.lunchtray.datasource.DataSource
import com.github.lunchtray.model.MenuItem
import com.github.lunchtray.onNodeWithStringId
import com.github.lunchtray.ui.EntreeMenuScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EntreeMenuScreenTest {
    @get: Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private val dataSource: List<MenuItem.EntreeItem> = DataSource.entreeMenuItems

    @Before
    fun init() {
        composeRule.setContent {
            EntreeMenuScreen(
                options = dataSource,
                onCancelButtonClicked = { },
                onNextButtonClicked = { },
                onSelectionChanged = { }
            )
        }
    }

    @Test
    fun entryMenuScreen_BackButtonIsEnabled() {
        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.back_button))
            .assertExists()
    }

    @Test
    fun entryMenuScreen_EnsureCorrectDataIsDisplayed() {
        DataSource.entreeMenuItems.forEach { entryMenuItem: MenuItem.EntreeItem ->
            composeRule.onNodeWithText(entryMenuItem.name).assertExists()
            composeRule.onNodeWithText(entryMenuItem.description).assertExists()
            composeRule.onNodeWithText(entryMenuItem.price.toString()).assertExists()
        }
    }

    @Test
    fun entryMenuScreen_BackButtonIsNotEnabledUntilEntryIsSelected() {
        composeRule.onNodeWithStringId(R.string.next, ignoreCase = true).assertIsNotEnabled()
        composeRule.chooseRandomEntry()
        composeRule.onNodeWithStringId(R.string.next, ignoreCase = true).assertIsEnabled()
    }
}
