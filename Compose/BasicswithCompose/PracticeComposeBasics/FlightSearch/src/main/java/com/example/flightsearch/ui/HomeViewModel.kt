package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.AppContainerProvider
import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.di.AppContainer
import com.example.flightsearch.data.repository.FlightsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class HomeViewModel : ViewModel() {
    abstract val uiState: StateFlow<FlightSearchUiState>
    abstract fun updateUserInput(input: String)
    abstract fun searchFlight(text: String)


    class Default(
        private val repository: FlightsRepository,
    ) : HomeViewModel() {
        override val uiState: MutableStateFlow<FlightSearchUiState> =
            MutableStateFlow(FlightSearchUiState("", searchResult = emptyList()))

        init {
            fetchData()
        }

        override fun updateUserInput(input: String) {
            uiState.update { it.copy(searchInput = input) }
        }

        override fun searchFlight(text: String) {
//            repository.getFlights()
        }

        private fun fetchData() {
            viewModelScope.launch {
                repository.getAllFlights().collect { airports ->
                    uiState.update { uiState ->
                        uiState.copy(searchResult = airports)
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            this.initializer {
                val appContainer: AppContainer = (this[APPLICATION_KEY] as AppContainerProvider).appContainer
                Default(appContainer.repository)
            }
        }
    }

    data class FlightSearchUiState(val searchInput: String, val searchResult: List<Airport>)
}

