package com.herasymchuk.cupcake.ui

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.herasymchuk.cupcake.R
import com.herasymchuk.cupcake.data.DataSource
import com.herasymchuk.cupcake.ui.theme.CupcakeTheme

enum class CupcakeScreen(@StringRes val title: Int) {
    START(title = R.string.app_name),
    FLAVOR(title = R.string.choose_flavor),
    PICKUP(title = R.string.choose_pickup_date),
    SUMMARY(title = R.string.order_summary),
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerColor: Color
    val titleContentColor: Color
    if (isSystemInDarkTheme()) {
        containerColor = MaterialTheme.colorScheme.primaryContainer
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        containerColor = MaterialTheme.colorScheme.primary
        titleContentColor = MaterialTheme.colorScheme.onPrimary
    }

    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = if (isSystemInDarkTheme()) Color.Black else Color.White
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

    val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentScreen: CupcakeScreen =
        CupcakeScreen.valueOf(backStackEntry?.destination?.route ?: CupcakeScreen.START.name)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.START.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .safeDrawingPadding()
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
                val context: Context = LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject, summary)
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

private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent: Intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}

@Preview
@Composable
private fun CupcakeAppBarPreview() {
    CupcakeTheme {
        CupcakeAppBar(currentScreen = CupcakeScreen.PICKUP, canNavigateBack = true, navigateUp = { /*TODO*/ })
    }
}

@Preview
@Composable
private fun CupcakeAppPreview() {
    CupcakeTheme {
        CupcakeApp()
    }
}
