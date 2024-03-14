package com.herasymchuk.cupcake.tests.navigation

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.herasymchuk.cupcake.R
import com.herasymchuk.cupcake.assertCurrentRouteName
import com.herasymchuk.cupcake.data.DataSource
import com.herasymchuk.cupcake.onNodeWithStringId
import com.herasymchuk.cupcake.ui.CupcakeApp
import com.herasymchuk.cupcake.ui.CupcakeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CupcakeScreenNavigationTest {
    @get: Rule
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule()
    private val testCupcakeNavigation = TestCupcakeNavigation(composeTestRule)
    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }
    }

    @Test
    fun cupcakeNavHost_VerifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.START.name)
    }

    @Test
    fun cupcakeNavHost_VerifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun cupcakeNavHost_ClickOneCupcake_NavigatesToSelectFlavorScreen() {
        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.FLAVOR.name)
    }

    @Test
    fun cupcakeNavHost_NavigateToSelectFlavorScreen_NextButtonIsDisabledUntilOneOptionIsSelected() {
        testCupcakeNavigation.navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
        // Random string id from displayed flavors
        composeTestRule.onNodeWithStringId(DataSource.flavors.random()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    @Test
    fun cupcakeNavHost_NavigateToStartScreenFromFlavorScreen() {
        testCupcakeNavigation.navigateToFlavorScreen()
        testCupcakeNavigation.navigateToStartScreen()
        navController.assertCurrentRouteName(CupcakeScreen.START.name)
    }

    @Test
    fun cupcakeNavHost_FlavorScreenCancelOrder_NavigatesToStartScreen() {
        testCupcakeNavigation.navigateToFlavorScreen()
        testCupcakeNavigation.clickCancel()
        navController.assertCurrentRouteName(CupcakeScreen.START.name)
    }

    @Test
    fun cupcakeNavHost_FlavorScreenSelectAndClickNext_NavigatesToPickupScreen() {
        testCupcakeNavigation.navigateToPickupScreen()
        navController.assertCurrentRouteName(CupcakeScreen.PICKUP.name)
    }

    @Test
    fun cupcakeNavHost_PickupScreenClickBackButton_NavigatesToFlavorScreen() {
        testCupcakeNavigation.navigateToPickupScreen()
        testCupcakeNavigation.clickNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.FLAVOR.name)
    }

    @Test
    fun cupcakeNavHost_NavigationToSummaryScreen() {
        testCupcakeNavigation.navigateToSummaryScreen()
        navController.assertCurrentRouteName(CupcakeScreen.SUMMARY.name)
    }

    @Test
    fun cupcakeNavHost_SummaryScreenClickCancel_NavigatesToStartScreen() {
        testCupcakeNavigation.navigateToSummaryScreen()
        testCupcakeNavigation.clickCancel()
    }
}

