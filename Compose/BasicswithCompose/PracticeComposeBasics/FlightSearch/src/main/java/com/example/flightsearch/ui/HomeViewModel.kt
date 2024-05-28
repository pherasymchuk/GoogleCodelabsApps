package com.example.flightsearch.ui

import android.util.Log
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "LOG"


abstract class HomeViewModel : ViewModel() {
    abstract val uiState: StateFlow<HomeUiState>
    abstract fun onSearchInputChange(input: String)

    data class HomeUiState(val searchInput: String, val searchResult: List<Airport>)

    class Default(
        private val repository: FlightsRepository,
    ) : HomeViewModel() {
        override val uiState: MutableStateFlow<HomeUiState> =
            MutableStateFlow(HomeUiState("", searchResult = emptyList()))
        private var searchJob: Job? = null

        init {
            fetchData()
            setupSearch()
        }

        override fun onSearchInputChange(input: String) {
            uiState.update { it.copy(searchInput = input) }
        }

        @OptIn(FlowPreview::class)
        private fun setupSearch() {
            viewModelScope.launch {
                uiState
                    .map { it.searchInput }
                    .debounce(300)  // Adjust debounce time as needed
                    .distinctUntilChanged()
                    .collectLatest { query ->
                        searchJob?.cancel()  // Cancel the previous job if a new query comes in
                        searchJob = launch {
                            if (query.isNotBlank()) {
                                searchFlight()
                            } else {
                                fetchData()
                            }
                        }
                    }
            }
        }

        private fun searchFlight() {
            val input = uiState.value.searchInput

            searchJob = viewModelScope.launch {
                repository.searchFlights(input)
                    .collect { airports ->
                        Log.d(TAG, "searchFlights: Collecting")
                        uiState.update { oldState ->
                            oldState.copy(searchResult = airports)
                        }
                    }
            }
        }

        private fun fetchData() {
            viewModelScope.launch {
                repository.getAllFlights().collect { airports ->
                    uiState.update { oldState ->
                        oldState.copy(searchResult = airports)
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

}

