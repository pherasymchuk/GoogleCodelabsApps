package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.AppContainerProvider
import com.example.flightsearch.data.di.AppContainer
import com.example.flightsearch.data.repository.FlightsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class HomeViewModel : ViewModel() {
    abstract val uiState: StateFlow<FlightSearchUiState>


    class Default(
        private val repository: FlightsRepository,
    ) : HomeViewModel() {
        override val uiState: MutableStateFlow<FlightSearchUiState> =
            MutableStateFlow(FlightSearchUiState(""))
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            this.initializer {
                val appContainer: AppContainer = (this[APPLICATION_KEY] as AppContainerProvider).appContainer
                Default(appContainer.repository)
            }
        }
    }
}

data class FlightSearchUiState(val searchInput: String)
