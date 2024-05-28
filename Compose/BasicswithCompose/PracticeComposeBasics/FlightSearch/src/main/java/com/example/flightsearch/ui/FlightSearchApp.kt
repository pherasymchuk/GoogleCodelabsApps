package com.example.flightsearch.ui

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flightsearch.R
import com.example.flightsearch.ui.screens.FlightSearchAppBar
import com.example.flightsearch.ui.screens.Home
import com.example.flightsearch.ui.screens.HomeScreen

@Composable
fun FlightSearchApp(
    viewModel: HomeViewModel = viewModel<HomeViewModel.Default>(factory = HomeViewModel.Factory),
) {
    val uiState: HomeViewModel.HomeUiState by viewModel.uiState.collectAsState()

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

        NavHost(navController = navController, startDestination = Home) {
            composable<Home> {
                HomeScreen(
                    uiState = uiState,
                    onValueChange = { input: String ->
                        viewModel.onSearchInputChange(input)
                    },
                    innerPadding = innerPadding,
                    modifier = Modifier,
                )
            }
        }
    }
}


