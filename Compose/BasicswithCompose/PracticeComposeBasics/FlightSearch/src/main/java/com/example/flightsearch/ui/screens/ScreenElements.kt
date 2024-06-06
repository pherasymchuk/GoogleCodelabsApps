package com.example.flightsearch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.ui.model.UiAirport
import com.example.flightsearch.ui.model.UiFlight
import com.example.flightsearch.ui.theme.FlightSearchTheme
import com.github.compose.InputTextField

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
    uiState: HomeViewModel.HomeUiState,
    modifier: Modifier = Modifier,
    itemPadding: Dp = 0.dp,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onAirportClick: (airport: UiAirport) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
            end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
        ),
        modifier = modifier.consumeWindowInsets(innerPadding)
    ) {
        items(uiState.searchResult) { airport ->
            AirportItem(
                airport = airport,
                onClick = { onAirportClick(airport) },
                modifier = Modifier
                    .padding(itemPadding)
                    .fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.size(innerPadding.calculateBottomPadding()))
        }
    }
}

@Composable
fun FlightList(
    modifier: Modifier = Modifier,
    flights: List<UiFlight>,
    onFavoriteClick: (flight: UiFlight) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                stringResource(R.string.flights_from, flights.first().departureAirport.iataCode),
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
        this.items(items = flights) { flight: UiFlight ->
            FlightCard(
                flight = flight,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
fun AirportItem(
    airport: UiAirport,
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    onClick: (id: Int) -> Unit = {},
) {
    Row(
        modifier = modifier.clickable(onClick = { onClick(airport.id) }, enabled = clickable)
    ) {
        Text(
            text = airport.iataCode,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alignByBaseline()
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = airport.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alignByBaseline()
        )
    }
}

@Composable
fun SearchTextField(
    uiState: HomeViewModel.HomeUiState,
    modifier: Modifier = Modifier,
    onInput: (String) -> Unit,
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

        onValueChange = onInput,
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    )
}


@Preview
@Composable
private fun AirportItemPreview() {
    FlightSearchTheme {
        AirportItem(
            airport = UiAirport(
                id = 0,
                name = "Leonardo da Vinci International Airport",
                iataCode = "FCO"
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchResultPreview() {
    FlightSearchTheme {
        val fakeAirports = List(7) {
            UiAirport(
                id = it,
                name = "Airport ${it + 1}",
                iataCode = "IATA${it + 1}",
            )
        }
        SearchResult(
            uiState = HomeViewModel.HomeUiState(
                searchInput = "",
                searchResult = fakeAirports,
                favoriteFlights = emptyList()
            )
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
        uiState = HomeViewModel.HomeUiState(
            searchInput = "input here",
            searchResult = listOf(
                UiAirport(
                    id = 1,
                    name = "Airport name",
                    iataCode = "code",
                )
            ),
            favoriteFlights = emptyList()
        ),
        modifier = Modifier.padding(4.dp),
        onInput = {}
    )
}
