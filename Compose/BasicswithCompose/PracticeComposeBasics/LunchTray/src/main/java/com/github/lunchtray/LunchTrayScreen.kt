package com.github.lunchtray

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.lunchtray.ui.OrderViewModel

enum class LunchTrayScreen(@StringRes val title: Int) {
    START(R.string.app_name),
    ENTRY_MENU(R.string.choose_entree),
    SIDE_DISH_MENU(R.string.choose_side_dish),
    ACCOMPANIMENT_MENU(R.string.choose_accompaniment),
    CHECKOUT(R.string.order_checkout)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp() {
    // TODO: Create Controller and initialization

    // Create ViewModel
    val viewModel: OrderViewModel = viewModel<OrderViewModel.Base>()

    Scaffold(
        topBar = {
            // TODO: AppBar
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        // TODO: Navigation host
    }
}
