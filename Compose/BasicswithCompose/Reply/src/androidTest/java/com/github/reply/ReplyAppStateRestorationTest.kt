package com.github.reply

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.github.reply.data.local.LocalEmailsDataProvider
import com.github.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppStateRestorationTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_PerformConfigChange_SelectedEmailRetained() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent { ReplyApp(windowSize = WindowWidthSizeClass.Compact) }

        // Given third email is displayed
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body))
            .assertIsDisplayed()

        // Open detailed page
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject))
            .performClick()

        // Verify that it shows the detailed screen for the correct email
        composeTestRule.onNodeWithContentDescriptionForStringId(R.string.navigation_back)
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body))
            .assertExists()

        stateRestorationTester.emulateSavedInstanceStateRestore()

        // Verify that it still shows the detailed screen for the same email
        composeTestRule.onNodeWithContentDescriptionForStringId(R.string.navigation_back)
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body))
            .assertExists()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_PerformConfigChange_SelectedEmailRetained() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent { ReplyApp(windowSize = WindowWidthSizeClass.Expanded) }

        // Given second
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[1].body)
        ).assertIsDisplayed()

        // Select second email
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[1].subject)
        ).performClick()

        // Verify that the second email is displayed on the details screen
        // Here I checking on children of the details screen, because home screen also contains
        // that item
        composeTestRule.onNodeWithTagForStringId(R.string.details_screen).onChildren()
            .assertAny(
                hasAnyDescendant(
                    hasText(
                        composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[1].body)
                    )
                )
            )

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithTagForStringId(R.string.details_screen).onChildren()
            .assertAny(
                hasAnyDescendant(
                    hasText(
                        composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[1].body)
                    )
                )
            )
    }
}
