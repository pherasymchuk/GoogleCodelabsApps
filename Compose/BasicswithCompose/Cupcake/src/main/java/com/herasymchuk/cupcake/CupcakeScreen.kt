package com.herasymchuk.cupcake.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.herasymchuk.cupcake.R
import com.herasymchuk.cupcake.data.DataSource

enum class CupcakeScreen {
    START,
    FLAVOR,
    PICKUP,
    SUMMARY,
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel<OrderViewModel.Base>(),
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        topBar = {
            CupcakeAppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.START.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(CupcakeScreen.START.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = { numberOfCupcakes: Int ->
                        viewModel.setQuantity(numberOfCupcakes)
                        navController.navigate(CupcakeScreen.FLAVOR.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }

            composable(CupcakeScreen.FLAVOR.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = DataSource.flavors.map { stringResource(id = it) },
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.PICKUP.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(CupcakeScreen.PICKUP.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = uiState.pickupOptions,
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.SUMMARY.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSelectionChanged = { viewModel.setDate(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(CupcakeScreen.SUMMARY.name) {
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onSendButtonClicked = { subject: String, summary: String ->

                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController,
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.START.name, inclusive = false)
}
