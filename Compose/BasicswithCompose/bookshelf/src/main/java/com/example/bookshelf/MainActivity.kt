package com.example.bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.screens.HomeScreen
import com.example.bookshelf.ui.screens.HomeViewModel
import com.example.bookshelf.ui.theme.BookShelfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookShelfTheme {
                BookShelf()
            }
        }
    }
}

@Composable
fun BookShelf(modifier: Modifier = Modifier) {
    val viewModel = viewModel(HomeViewModel.Default::class.java, factory = HomeViewModel.Factory)
    Scaffold {
        HomeScreen(innerPadding = it, uiState = viewModel.uiState)
    }
}
