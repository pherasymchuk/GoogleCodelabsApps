package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    uiState: BooksUiState,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    val text = when (uiState) {
        is BooksUiState.Loading -> {
            "Loading"
        }

        is BooksUiState.Error -> {
            "Error"
        }

        is BooksUiState.Success -> {
            "Total items: ${uiState.booksResults.totalItems}"
        }
    }
    Text(text = text, modifier = Modifier.padding(innerPadding))
}

@Preview
@Composable
private fun HomeScreen() {

}
