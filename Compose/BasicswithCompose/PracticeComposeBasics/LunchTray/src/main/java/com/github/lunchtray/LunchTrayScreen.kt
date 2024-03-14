package com.github.lunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.lunchtray.datasource.DataSource
import com.github.lunchtray.model.OrderUiState
import com.github.lunchtray.ui.AccompanimentMenuScreen
import com.github.lunchtray.ui.CheckoutScreen
import com.github.lunchtray.ui.EntreeMenuScreen
import com.github.lunchtray.ui.OrderViewModel
import com.github.lunchtray.ui.SideDishMenuScreen
import com.github.lunchtray.ui.StartOrderScreen
import com.github.lunchtray.ui.theme.LunchTrayTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class LunchTrayScreen(@StringRes val title: Int) {
    START(R.string.app_name),
    ENTRY_MENU(R.string.choose_entree),
    SIDE_DISH_MENU(R.string.choose_side_dish),
    ACCOMPANIMENT_MENU(R.string.choose_accompaniment),
    CHECKOUT(R.string.order_checkout)
}

@Composable
fun LunchTrayApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentScreen: LunchTrayScreen =
        LunchTrayScreen.valueOf(backStackEntry?.destination?.route ?: LunchTrayScreen.START.name)
    val viewModel: OrderViewModel = viewModel<OrderViewModel.Base>()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            LunchTrayAppBar(
                currentScreen = currentScreen,
                canNavigateUp = currentScreen != LunchTrayScreen.START,
                onNavigateUp = { navController.popBackStack() },
                modifier = Modifier.displayCutoutPadding()
            )
        }
    ) { innerPadding ->
        val uiState: OrderUiState by viewModel.uiState.collectAsState()
        val scaffoldState: ScrollState = rememberScrollState()

        // TODO: Navigation host
        NavHost(
            navController = navController,
            startDestination = LunchTrayScreen.START.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .safeDrawingPadding()
        ) {
            composable(LunchTrayScreen.START.name) {
                StartOrderScreen(onStartOrderButtonClicked = {
                    navController.navigate(LunchTrayScreen.ENTRY_MENU.name)
                })
            }
            composable(LunchTrayScreen.ENTRY_MENU.name) {
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    onCancelButtonClicked = {
                        cancelOrder(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(LunchTrayScreen.SIDE_DISH_MENU.name)
                    },
                    onSelectionChanged = { viewModel.updateEntree(it) }
                )
            }
            composable(LunchTrayScreen.SIDE_DISH_MENU.name) {
                SideDishMenuScreen(
                    options = DataSource.sideDishMenuItems,
                    onCancelButtonClicked = {
                        cancelOrder(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(LunchTrayScreen.ACCOMPANIMENT_MENU.name)
                    },
                    onSelectionChanged = { viewModel.updateSideDish(it) }
                )
            }
            composable(LunchTrayScreen.ACCOMPANIMENT_MENU.name) {
                AccompanimentMenuScreen(
                    options = DataSource.accompanimentMenuItems,
                    onCancelButtonClicked = {
                        cancelOrder(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(LunchTrayScreen.CHECKOUT.name)
                    },
                    onSelectionChanged = { viewModel.updateAccompaniment(it) }
                )
            }
            composable(LunchTrayScreen.CHECKOUT.name) {
                CheckoutScreen(
                    orderUiState = uiState,
                    onSubmitOrderButtonClicked = {
                        scope.launch { snackbarHostState.showSnackbar("Your order is submitted") }
                        cancelOrder(viewModel, navController)
                    },
                    onCancelButtonClicked = {
                        cancelOrder(viewModel, navController)
                    }
                )
            }
        }
    }
}

private fun cancelOrder(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(
        LunchTrayScreen.START.name,
        inclusive = false
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar(
    currentScreen: LunchTrayScreen,
    canNavigateUp: Boolean,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = LocalContext.current.getString(currentScreen.title),
                )
            }
        },
        navigationIcon = {
            if (canNavigateUp) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun LunchTrayAppBarPreview() {
    LunchTrayTheme {
        LunchTrayAppBar(
            currentScreen = LunchTrayScreen.CHECKOUT,
            canNavigateUp = true,
            onNavigateUp = {}
        )
    }
}

@Preview
@Composable
private fun LunchTrayScreenPreview() {
    LunchTrayTheme {
        LunchTrayApp()
    }
}
