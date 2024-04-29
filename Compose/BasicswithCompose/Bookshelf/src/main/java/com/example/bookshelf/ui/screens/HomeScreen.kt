package com.example.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.data.Item
import com.example.bookshelf.ui.theme.BookshelfTheme

@Composable
fun HomeScreen(
    uiState: BooksUiState,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onRetry: () -> Unit,
) {
    when (uiState) {
        is BooksUiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is BooksUiState.Error -> {
            ErrorScreen(modifier = modifier, onRetry = onRetry)
        }

        is BooksUiState.Success -> {
            val itemList: List<Item> by remember { mutableStateOf(uiState.booksResults.items) }
            BooksImagesGrid(
                modifier = modifier,
                itemList = itemList.reversed(),
                contentPadding = innerPadding,
                minItemSizeDp = 150.dp
            )
        }
    }
}

@Composable
fun BooksImagesGrid(
    modifier: Modifier = Modifier,
    itemList: List<Item>,
    minItemSizeDp: Dp,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val screenWidthDp: Dp = LocalConfiguration.current.screenWidthDp.dp
    val itemsPerRow: Int = (screenWidthDp / minItemSizeDp).toInt().coerceAtLeast(1)

    LazyColumn(modifier = modifier, contentPadding = contentPadding) {
        itemList.chunked(itemsPerRow).forEach { rowItems ->
            item {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    rowItems.forEach { item: Item ->
                        Column(
                            modifier = Modifier
                                .widthIn(min = minItemSizeDp)
                                .weight(1f)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.volumeInfo.imageLinks?.thumbnail?.replace("http", "https"))
                                    .error(R.drawable.no_image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
//                                    .width(200.dp)
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                            )
                            val textSize = 12.sp
                            Text(
                                text = item.volumeInfo.title,
                                fontSize = textSize,
                                maxLines = 2,
                                minLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, onRetry: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.error_loading_book_icon_without_text
            ), contentDescription = stringResource(
                R.string.error_loading_books
            ),
            modifier = Modifier.size(200.dp)
        )
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingScreenPreview() {
    BookshelfTheme {
        LoadingScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorScreenPreview() {
    BookshelfTheme {
        ErrorScreen(onRetry = {})
    }
}

@Preview
@Composable
private fun HomeScreen() {

}
