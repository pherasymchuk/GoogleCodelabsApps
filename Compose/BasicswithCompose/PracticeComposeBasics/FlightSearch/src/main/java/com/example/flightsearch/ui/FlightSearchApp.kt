package com.example.flightsearch.ui

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.flightsearch.R
import com.example.flightsearch.ui.model.UiAirport
import com.example.flightsearch.ui.screens.Details
import com.example.flightsearch.ui.screens.FlightDetails
import com.example.flightsearch.ui.screens.FlightSearchAppBar
import com.example.flightsearch.ui.screens.Home
import com.example.flightsearch.ui.screens.HomeScreen
import kotlin.reflect.typeOf

@Composable
fun FlightSearchApp() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = {
            FlightSearchAppBar(
                title = stringResource(R.string.app_name),
                modifier = Modifier
                    .height(85.dp)
                    .wrapContentHeight()
            )
        }
    ) { innerPadding ->

        val window: Window? = (LocalContext.current as? Activity)?.window
        val view: View = LocalView.current
        if (window != null && !view.isInEditMode) {
            SideEffect {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            }
        }

        NavHost(navController = navController,
            startDestination = Home,
            popEnterTransition = {
                this.slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
            }
        ) {
            composable<Home>(
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(500, easing = LinearEasing)
                    ) + scaleIn(tween(durationMillis = 500, easing = LinearEasing), initialScale = 0.8f)
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(500, easing = LinearEasing)
                    ) + scaleOut(tween(durationMillis = 500, easing = LinearEasing), targetScale = 0.8f)
                },
            ) {
                HomeScreen(
                    innerPadding = innerPadding,
                    onAirportClick = { navController.navigate(Details(it)) },
                    modifier = Modifier,
                )
            }

            composable<Details>(
                typeMap = mapOf(typeOf<UiAirport>() to UiAirport.NavType()),
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(500, easing = LinearEasing)
                    ) + slideIntoContainer(
                        animationSpec = tween(500, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Up
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(500, easing = LinearEasing)
                    ) + slideOutOfContainer(
                        animationSpec = tween(500, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.Down
                    )
                }
            ) { navBackStackEntry ->
                val airport: UiAirport = navBackStackEntry.toRoute<Details>().airport
                FlightDetails(
                    airport = airport,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(top = 12.dp),
                )
            }
        }
    }
}
