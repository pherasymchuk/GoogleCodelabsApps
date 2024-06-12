package com.example.flightsearch.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.ui.Destination
import com.example.flightsearch.ui.model.UiAirport
import com.example.flightsearch.ui.theme.FlightSearchTheme
import kotlinx.serialization.Serializable

@Serializable
object Home : Destination

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel<HomeViewModel.Default>(factory = HomeViewModel.Factory),
    onAirportClick: (UiAirport) -> Unit = {},
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    val uiState: HomeViewModel.HomeUiState by androidx.lifecycle.viewmodel.compose.viewModel.uiState.collectAsState()

    Column(modifier = modifier) {
        SearchTextField(
            uiState = uiState,
            onInput = viewModel::onSearchInputChange,
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
        FlightList(
            heading = "Favorite routes",
            flights = uiState.favoriteFlights,
            onFavoriteClick = viewModel
        )
        SearchResult(
            uiState = uiState,
            innerPadding = innerPadding,
            itemPadding = 1.dp,
            onAirportClick = onAirportClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .imePadding()
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    FlightSearchTheme {
        val fakeAirports = List(50) {
            UiAirport(
                id = it,
                name = "Airport ${it + 1}",
                iataCode = "IATA${it + 1}",
            )
        }
    }
}
