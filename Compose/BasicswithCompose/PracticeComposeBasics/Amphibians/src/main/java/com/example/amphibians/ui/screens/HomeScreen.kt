package com.example.amphibians.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.data.network.Amphibian

@Composable
fun HomeScreen(
    uiState: AmphibiansUiState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is AmphibiansUiState.Loading -> {
            LoadingScreen(
                contentPadding = contentPadding,
                modifier = modifier
            )
        }

        is AmphibiansUiState.Error -> {
            ErrorScreen(
                contentPadding = contentPadding,
                modifier = modifier
            )
        }

        is AmphibiansUiState.Success -> {
            AmphibianSuccessScreen(
                uiState.amphibians,
                contentPadding = contentPadding,
                modifier = modifier
            )
        }
    }
}

@Composable
fun AmphibianSuccessScreen(
    amphibians: List<Amphibian>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = contentPadding) {
        items(amphibians) {
            AmphibianCard(it, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier) {
            AmphibianCaption(amphibian = amphibian, modifier = Modifier.padding(8.dp))
            AmphibianImage(amphibian = amphibian)
            AmphibianDescription(amphibian = amphibian, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun AmphibianCaption(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Text(
        text = "${amphibian.name} (${amphibian.type})",
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun AmphibianImage(amphibian: Amphibian, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(amphibian.imgSrc)
            .build(),
        contentDescription = stringResource(R.string.amphibian_image),
        error = painterResource(id = R.drawable.error_loading_img),
        contentScale = ContentScale.FillWidth,
        placeholder = painterResource(id = R.drawable.error_loading_img),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun AmphibianDescription(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = amphibian.description)
    }
}

@Composable
fun ErrorScreen(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(contentPadding)) {
        painterResource(id = R.drawable.error_img)
    }
}

@Composable
fun LoadingScreen(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    Box(modifier.padding(contentPadding)) {
        Text(text = "Loading")
    }
}
