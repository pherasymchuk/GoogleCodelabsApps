package com.example.flightsearch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.ui.HomeViewModel
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
        onInput = {}
    )
}
