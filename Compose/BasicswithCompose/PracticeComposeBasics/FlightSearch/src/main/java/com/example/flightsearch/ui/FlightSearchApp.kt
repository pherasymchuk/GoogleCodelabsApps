package com.example.flightsearch.ui

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R

@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel<HomeViewModel.Default>(factory = HomeViewModel.Factory),
) {
    val uiState: FlightSearchUiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { FlightSearchAppBar(title = stringResource(R.string.app_name)) }
    ) { innerPadding ->
        val window = (LocalContext.current as? Activity)?.window
        val view = LocalView.current
        if (window != null && !view.isInEditMode) {
            SideEffect {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            }
        }
        Column(Modifier.padding(innerPadding)) {
            Text("Flight Search")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchAppBar(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    CenterAlignedTopAppBar(
        title = { Text(title, color = textColor) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
    )
}

@Composable
fun SearchBar(
    uiState: FlightSearchUiState,
    modifier: Modifier = Modifier,
) {
    TextField(value = uiState.searchInput, onValueChange = {})
}

@Preview
@Composable
private fun FlightSearchAppBarPreview() {
    FlightSearchAppBar(title = "App name")
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchBar(uiState = FlightSearchUiState("input here"))
}
