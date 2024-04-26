package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R

@Composable
fun HomeScreen(
    uiState: BooksUiState,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (uiState) {
        is BooksUiState.Loading -> {
            Text("Loading")
        }

        is BooksUiState.Error -> {
            Text("Error")
        }

        is BooksUiState.Success -> {
            val booksId: List<String> by remember {
                mutableStateOf(uiState.booksResults.items.map { it.id })
            }
            val booksThumbnails: List<String?> by remember {
                mutableStateOf(
                    uiState.booksResults.items.map {
                        it.volumeInfo.imageLinks?.thumbnail?.replace(
                            "http", "https"
                        )
                    }
                )
            }
            BooksImagesGrid(imgUrls = booksThumbnails, contentPadding = innerPadding)
        }
    }
}

@Composable
fun BooksImagesGrid(
    imgUrls: List<String?>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = contentPadding
    ) {
        this.items(imgUrls) {
            Box(modifier = Modifier.size(200.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .error(R.drawable.no_image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp, 200.dp)
                        .padding(1.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreen() {

}
