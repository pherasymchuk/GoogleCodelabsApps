package com.example.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AppContainerProvider
import com.example.amphibians.data.network.Amphibian
import com.example.amphibians.data.repositories.AmphibiansRepository
import kotlinx.coroutines.launch

sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState
    data object Error : AmphibiansUiState
    data object Loading : AmphibiansUiState
}

abstract class HomeViewModel(protected val repository: AmphibiansRepository) : ViewModel() {
    abstract val amphibiansUiState: AmphibiansUiState

    abstract fun fetchAmphibians()

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val containerProvider =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AppContainerProvider

                Default(containerProvider.getAppContainer().amphibiansRepository)
            }
        }
    }

    class Default(repository: AmphibiansRepository) : HomeViewModel(repository) {
        override var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)

        init {
            fetchAmphibians()
        }

        override fun fetchAmphibians() {
            amphibiansUiState = AmphibiansUiState.Loading
            viewModelScope.launch {
                try {
                    val amphibians = repository.getAmphibians()
                    amphibiansUiState = AmphibiansUiState.Success(amphibians)
                } catch (e: Exception) {
                    amphibiansUiState = AmphibiansUiState.Error
                }
            }
        }
    }
}
