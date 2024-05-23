package com.example.flightsearch.ui

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.ui.theme.FlightSearchTheme
import com.github.compose.InputTextField

@Composable
fun FlightSearchApp(
    viewModel: HomeViewModel = viewModel<HomeViewModel.Default>(factory = HomeViewModel.Factory),
) {
    val uiState: HomeViewModel.FlightSearchUiState by viewModel.uiState.collectAsState()

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

        HomeScreen(
            uiState = uiState,
            onValueChange = viewModel::updateUserInput,
            innerPadding = innerPadding,
            modifier = Modifier,
        )
    }
}

@Composable
fun HomeScreen(
    uiState: HomeViewModel.FlightSearchUiState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(modifier = modifier) {
        SearchTextField(
            uiState = uiState,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp
                )
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                )
                .height(45.dp)
                .requiredHeight(45.dp)
        )
        SearchResult(
            uiState = uiState,
            innerPadding = innerPadding,
            itemPadding = 1.dp,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        )
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
fun SearchResult(
    uiState: HomeViewModel.FlightSearchUiState,
    modifier: Modifier = Modifier,
    itemPadding: Dp = 0.dp,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
            end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
        ),
        modifier = modifier.consumeWindowInsets(innerPadding)
    ) {
        items(uiState.searchResult) {
            AirportItem(identifier = it.iataCode, name = it.name, modifier = Modifier.padding(itemPadding))
        }
        item {
            Spacer(modifier = Modifier.size(innerPadding.calculateBottomPadding()))
        }
    }
}

@Composable
fun AirportItem(
    identifier: String,
    name: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Text(text = identifier, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = name, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SearchTextField(
    uiState: HomeViewModel.FlightSearchUiState,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    InputTextField(
        value = uiState.searchInput,
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

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    FlightSearchTheme {
        HomeScreen(
            uiState = HomeViewModel.FlightSearchUiState(
                "",
                searchResult = listOf(
                    Airport(
                        1,
                        name = "Airport name",
                        iataCode = "code",
                        passengers = 9
                    )
                )
            ),
            onValueChange = {}
        )
    }
}

@Preview
@Composable
private fun AirportItemPreview() {
    FlightSearchTheme {
        AirportItem(
            identifier = "FCO",
            name = "Leonardo da Vinci International Airport"
        )
    }
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
        uiState = HomeViewModel.FlightSearchUiState(
            "input here", searchResult = listOf(
                Airport(
                    1,
                    name = "Airport name",
                    iataCode = "code",
                    passengers = 9
                )
            )
        ),
        modifier = Modifier.padding(4.dp),
        onValueChange = {}
    )
}
