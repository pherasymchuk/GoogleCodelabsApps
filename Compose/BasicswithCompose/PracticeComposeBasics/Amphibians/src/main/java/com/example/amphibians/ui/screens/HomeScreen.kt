package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.data.network.Amphibian
import com.example.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    uiState: AmphibiansUiState,
    contentPadding: PaddingValues,
    onRetry: () -> Unit,
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
                onRetry = onRetry,
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
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        columns = GridCells.Adaptive(minSize = 400.dp)
    ) {
        items(amphibians) {
            AmphibianCard(
                amphibian = it,
                modifier = Modifier
                    .padding(8.dp)
                    .widthIn(0.dp, 400.dp)
            )
        }
    }
}

@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            AmphibianCaption(amphibian = amphibian, modifier = Modifier.padding(16.dp))
            AmphibianImage(amphibian = amphibian, modifier = Modifier.size(width = 400.dp, height = 200.dp))
            AmphibianDescription(amphibian = amphibian, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun AmphibianCaption(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Text(
        text = "${amphibian.name} (${amphibian.type})",
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}

@Composable
fun AmphibianImage(amphibian: Amphibian, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(amphibian.imgSrc)
            .error(R.drawable.error_loading_img)
            .build(),

        contentDescription = stringResource(R.string.amphibian_image),
        contentScale = ContentScale.FillWidth,
        modifier = modifier.fillMaxWidth(),
        loading = {
            Box(modifier = Modifier) {
                Image(
                    painter = painterResource(id = R.drawable.img_placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    trackColor = MaterialTheme.colorScheme.primary,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        error = {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.error_loading_img),
                    contentDescription = stringResource(R.string.error_loading_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    )
}

@Composable
fun AmphibianDescription(
    amphibian: Amphibian,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(text = amphibian.description, fontSize = 16.sp)
    }
}

@Composable
fun ErrorScreen(
    contentPadding: PaddingValues,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(
                painter = painterResource(id = R.drawable.img_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(200.dp)
            )
            Text(
                text = "Error occurred while loading data",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Button(onClick = onRetry,
                Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun LoadingScreen(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        Text(text = "Loading", modifier = Modifier.align(Alignment.TopCenter))
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.Center),
            trackColor = MaterialTheme.colorScheme.primary,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    AmphibiansTheme {
        ErrorScreen(contentPadding = PaddingValues(0.dp), onRetry = {})
    }
}
