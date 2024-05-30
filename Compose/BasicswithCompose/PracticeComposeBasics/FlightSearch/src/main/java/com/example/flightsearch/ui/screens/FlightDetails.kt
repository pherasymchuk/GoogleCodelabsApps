package com.example.flightsearch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.database.model.Airport
import com.example.flightsearch.ui.Destination
import com.example.flightsearch.ui.theme.FlightSearchTheme
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
class Details(val airport: Airport) : Destination

@Composable
fun FlightDetails(
    airport: Airport,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel<DetailsViewModel.Default>(
        factory = DetailsViewModel.provideFactory(airport)
    ),
) {
    val uiState: DetailsViewModel.DetailsUiState by viewModel.uiState.collectAsState()

    LazyColumn(modifier = modifier) {
        this.items(items = uiState.arrivalAirports, key = { it.id }) { arrivalAirport: Airport ->
            FlightCard(
                departureAirport = uiState.selectedAirport,
                arrivalAirport = arrivalAirport,
                isFavorite = false, //Todo implement
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun FlightCard(
    departureAirport: Airport,
    arrivalAirport: Airport,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier) {
                Text(stringResource(R.string.depart), style = MaterialTheme.typography.labelMedium)
                AirportItem(airport = departureAirport, clickable = false)
                Text(stringResource(R.string.arrive), style = MaterialTheme.typography.labelMedium)
                AirportItem(airport = arrivalAirport, clickable = false)
            }
            Image(
                painter = painterResource(
                    if (isFavorite) {
                        R.drawable.ic_star_filled
                    } else {
                        R.drawable.ic_star_outlined
                    }
                ),
                contentDescription = "Is in favorite",
                modifier = Modifier
                    .weight(1f)
                    .size(40.dp)
                    .align(Alignment.CenterVertically),
                alignment = Alignment.CenterEnd
            )
        }
    }
}

@Preview
@Composable
private fun FlightCardPreview(
    @PreviewParameter(provider = FlightCardParameters::class)
    isFavorite: Boolean,
) {
    FlightSearchTheme {
        FlightCard(
            modifier = Modifier.fillMaxWidth(),
            departureAirport = Airport(
                id = 0,
                name = "Leonardo da Vinci International Airport",
                iataCode = "FCO",
                passengers = 1
            ),
            arrivalAirport = Airport(
                id = 1,
                name = "Dublin Airport",
                iataCode = "DUB",
                passengers = 1
            ),
            isFavorite = isFavorite
        )
    }
}

class FlightCardParameters : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}

@Preview
@Composable
private fun FlightDetailsPreview() {
    FlightSearchTheme {
        val fakeFlights = List(5) {
            Airport(
                id = it + 1,
                name = "Arrive Airport 1",
                iataCode = "AA${it + 1}",
                passengers = Random.nextInt(30, 200)
            )
        }

    }
}
