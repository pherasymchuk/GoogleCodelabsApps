package com.example.bookshelf.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.AppContainerProvider
import com.example.bookshelf.data.BooksResponse
import com.example.bookshelf.data.repositories.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "LOG_BOOKS"

sealed interface BooksUiState {
    data object Loading : BooksUiState
    data class Success(val booksResults: BooksResponse) : BooksUiState
    data object Error : BooksUiState
}

abstract class HomeViewModel(protected val repository: BooksRepository) : ViewModel() {
    abstract val uiState: BooksUiState

    abstract fun getBooks()

    class Default(repository: BooksRepository) : HomeViewModel(repository) {
        override var uiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private val defQuery = "android app"

        init {
            getBooks()
        }

        override fun getBooks() {
            viewModelScope.launch {
                try {
                    val booksResponse: BooksResponse = repository.getBooks(query = defQuery)
                    withContext(Dispatchers.Default) {
                        booksResponse.items.onEach {
                            it.volumeInfo.imageLinks?.thumbnail?.replace("http", "https")
                        }
                    }
                    uiState = BooksUiState.Success(booksResponse)
                } catch (e: Exception) {
                    uiState = BooksUiState.Error
                    Log.e(TAG, "getBooks: Error fetching books -> ", e.cause)
                }
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val appContainer = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AppContainerProvider).getAppContainer()
                val repo = appContainer.booksRepository
                Default(repo)
            }
        }
    }
}
