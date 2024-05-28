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
import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.ui.Destination
import com.example.flightsearch.ui.HomeViewModel
import com.example.flightsearch.ui.theme.FlightSearchTheme
import kotlinx.serialization.Serializable

@Serializable
object Home : Destination

@Composable
fun HomeScreen(
    uiState: HomeViewModel.HomeUiState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(modifier = modifier) {
        SearchTextField(
            uiState = uiState,
            onInput = onValueChange,
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
                .imePadding()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    FlightSearchTheme {
        HomeScreen(
            uiState = HomeViewModel.HomeUiState(
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
