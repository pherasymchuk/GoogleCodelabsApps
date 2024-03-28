package com.github.reply

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_VerifyUsingOnlyBottomNavigation() {
        composeTestRule.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_bottom)
            .assertExists()
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_rail)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_drawer)
            .assertDoesNotExist()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_VerifyUsingNavigationRail() {
        composeTestRule.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Medium)
        }
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_bottom)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_rail)
            .assertExists()
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_drawer)
            .assertDoesNotExist()
    }

    @Test
    @TestExpandedWidth
    fun largeDevice_VerifyUsingNavigationDrawer() {
        composeTestRule.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
        }
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_bottom)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_rail)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_drawer)
            .assertExists()
    }
}
