package com.example.flightsearch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.ui.Destination
import com.example.flightsearch.ui.model.UiAirport
import com.example.flightsearch.ui.model.UiFlight
import com.example.flightsearch.ui.theme.FlightSearchTheme
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
class Details(val airport: UiAirport) : Destination

@Composable
fun FlightDetails(
    airport: UiAirport,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel<DetailsViewModel.Default>(
        factory = DetailsViewModel.provideFactory(airport)
    ),
) {
    val uiState: DetailsViewModel.DetailsUiState by viewModel.uiState.collectAsState()
    val flights: List<UiFlight> = uiState.flights

    if (flights.isEmpty()) {
        StubFlights(modifier = modifier)
    } else {
        FlightList(
            modifier = modifier,
            flights = flights,
            onFavoriteClick = viewModel::saveOrRemoveFlightFromFavorites
        )
    }
}

@Composable
fun StubFlights(modifier: Modifier = Modifier) {

    val flights by remember {
        derivedStateOf {
            List(5) {
                UiFlight(
                    id = it,
                    departureAirport = UiAirport(id = it * 2, name = "", iataCode = ""),
                    arrivalAirport = UiAirport(id = it * 3, name = "", iataCode = ""),
                    isFavorite = false
                )
            }
        }
    }
    FlightList(
        modifier = modifier,
        flights = flights,
        onFavoriteClick = {}
    )
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
fun FlightCard(
    flight: UiFlight,
    modifier: Modifier = Modifier,
    onFavoriteClick: (flight: UiFlight) -> Unit,
) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.depart), style = MaterialTheme.typography.labelMedium)
                AirportItem(airport = flight.departureAirport, clickable = false)
                Text(stringResource(R.string.arrive), style = MaterialTheme.typography.labelMedium)
                AirportItem(airport = flight.arrivalAirport, clickable = false)
            }
            Image(
                painter = painterResource(
                    if (flight.isFavorite) {
                        R.drawable.ic_star_filled
                    } else {
                        R.drawable.ic_star_outlined
                    }
                ),
                contentDescription = "Is in favorite",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onFavoriteClick(flight)
                    }
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
            flight = UiFlight(
                id = 0,
                departureAirport = UiAirport(
                    id = 0,
                    name = "Leonardo da Vinci International Airport",
                    iataCode = "FCO",
                ),
                arrivalAirport = UiAirport(
                    id = 1,
                    name = "Dublin Airport",
                    iataCode = "DUB",
                ),
                isFavorite = isFavorite
            ),
            onFavoriteClick = {}
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
            UiFlight(
                id = 0,
                departureAirport = UiAirport(
                    id = 0,
                    name = "Leonardo da Vinci International Airport",
                    iataCode = "FCO",
                ),
                arrivalAirport = UiAirport(
                    id = 1,
                    name = "Dublin Airport",
                    iataCode = "DUB",
                ),
                isFavorite = Random.nextBoolean()
            )
        }
        FlightList(flights = fakeFlights, onFavoriteClick = {})
    }
}
