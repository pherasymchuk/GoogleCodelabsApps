package com.example.amphibians.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amphibians.ui.screens.HomeScreen
import com.example.amphibians.ui.screens.HomeViewModel
import com.example.amphibians.ui.theme.AmphibiansTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmphibiansTheme {
                AmphibiansApp()
            }
        }
    }
}


@Composable
fun AmphibiansApp(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        modelClass = HomeViewModel.Default::class.java,
        factory = HomeViewModel.Factory
    )
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        HomeScreen(uiState = viewModel.amphibiansUiState, contentPadding = innerPadding)
    }
}
