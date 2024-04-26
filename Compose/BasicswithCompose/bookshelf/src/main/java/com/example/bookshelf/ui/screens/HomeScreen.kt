package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

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
            val firstImageUrl = uiState.booksResults.items.first().volumeInfo.imageLinks?.thumbnail
                ?.replace("http", "https")
            Column {
                Text(
                    text = "Total items: ${uiState.booksResults.totalItems} \n" +
                            "First imageUrl: $firstImageUrl", modifier = Modifier.padding(innerPadding)
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(firstImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp, 300.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreen() {

}
