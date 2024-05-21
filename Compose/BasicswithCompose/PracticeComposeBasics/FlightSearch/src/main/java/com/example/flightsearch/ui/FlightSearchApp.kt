package com.example.flightsearch.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R

@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel<HomeViewModel.Default>(factory = HomeViewModel.Factory),
) {
    val uiState: FlightSearchUiState by viewModel.uiState.collectAsState()
    var input by rememberSaveable { mutableStateOf("") }

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
        val window = (LocalContext.current as? Activity)?.window
        val view = LocalView.current
        if (window != null && !view.isInEditMode) {
            SideEffect {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            }
        }
        Column(modifier = modifier.padding(innerPadding)) {
            SearchTextField(
                input = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(45.dp)
                    .requiredHeight(45.dp)
            )
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
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
        modifier = modifier
    )
}

@Composable
fun SearchTextField(
    input: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    com.github.compose.InputTextField(
        value = input,
        leadingIcon = {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            Image(
                painter = painterResource(R.drawable.ic_microphone),
                contentDescription = "Microphone"
            )
        },

        onValueChange = onValueChange,
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    )
}

@Preview
@Composable
private fun FlightSearchAppBarPreview() {
    FlightSearchAppBar(title = "App name")
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchTextField(
//        uiState = FlightSearchUiState("input here"),
        modifier = Modifier.padding(4.dp),
        onValueChange = {},
        input = "Text input"
    )
}
