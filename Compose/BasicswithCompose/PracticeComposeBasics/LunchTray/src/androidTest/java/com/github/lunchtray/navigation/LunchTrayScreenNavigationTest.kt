package com.github.lunchtray.navigation

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.github.lunchtray.LunchTrayApp
import com.github.lunchtray.LunchTrayScreen
import com.github.lunchtray.R
import com.github.lunchtray.assertCurrentRouteName
import com.github.lunchtray.chooseRandomEntry
import com.github.lunchtray.onNodeWithStringId
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LunchTrayScreenNavigationTest {
    @get: Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController
    private val navigation: LunchTrayNavigation = LunchTrayNavigation(composeRule)

    @Before
    fun setupLunchTrayNavHost() {
        composeRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                this.navigatorProvider.addNavigator(ComposeNavigator())
            }
            LunchTrayApp(navController = navController)
        }
    }

    @Test
    fun lunchTrayNavHost_VerifyStartDestination() {
        navController.assertCurrentRouteName(LunchTrayScreen.START.name)
    }

    @Test
    fun lunchTrayNavHost_ClickStartOder_NavigatesToEntreeMenuScreen() {
        navigation.toEntryMenuScreen()
        navController.assertCurrentRouteName(LunchTrayScreen.ENTRY_MENU.name)
    }

    @Test
    fun lunchTrayNavHost_EntryMenuScreenNavigateToStartScreenFromEntryMenuScreen() {
        navigation.toEntryMenuScreen()
        navigation.returnToStartScreen()
        navController.assertCurrentRouteName(LunchTrayScreen.START.name)
    }

    @Test
    fun lunchTrayNavHost_SideDishScreenClickBack_NavigatesToChooseEntryScreen() {
        navigation.toSideDishMenuScreen()
        navigation.clickBack()
        navController.assertCurrentRouteName(LunchTrayScreen.ENTRY_MENU.name)
    }

    @Test
    fun lunchTrayNavHost_NavigateToEntreeMenuScreen_NextButtonIsDisabledUntilOneOptionIsSelected() {
        navigation.toEntryMenuScreen()
        composeRule.onNodeWithStringId(R.string.next, ignoreCase = true).assertIsNotEnabled()
        // Choose random entree
        composeRule.chooseRandomEntry()
        composeRule.onNodeWithStringId(R.string.next, true).assertIsEnabled()
    }
}
