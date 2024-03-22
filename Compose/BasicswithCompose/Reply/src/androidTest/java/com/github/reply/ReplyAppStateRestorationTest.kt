package com.github.reply

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
    fun compactDevice_SelectedEmailRetained_AfterConfigChange() {
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
}
