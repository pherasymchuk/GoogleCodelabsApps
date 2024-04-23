package com.example.amphibians.ui.screens

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R

@Composable
fun HomeScreen(uiState: AmphibiansUiState, modifier: Modifier = Modifier) {

}

@Composable
fun AmphibianCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {

    }
}

@Composable
fun AmphibianCaption(modifier: Modifier = Modifier) {

}

@Composable
fun AmphibianImage(imgUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .build(),
        contentDescription = stringResource(R.string.amphibian_image)
    )
}

@Composable
fun AmphibianDescription(modifier: Modifier = Modifier) {

}
