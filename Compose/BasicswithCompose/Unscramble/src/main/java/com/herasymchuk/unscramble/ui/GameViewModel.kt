package com.herasymchuk.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herasymchuk.unscramble.model.SCORE_INCREASE
import com.herasymchuk.unscramble.model.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class GameViewModel<T> : ViewModel() {
    abstract val uiState: StateFlow<T>
    abstract val userGuess: Word

    abstract fun resetGame()
    abstract fun updateUserGuess(guessedWord: Word)
    abstract fun checkUserGuess()
    abstract fun updateGameState(updatedScore: Int)
    abstract fun scipWord()

    class Base : GameViewModel<GameUiState>() {
        private var randomWords = RandomWords(allWords)
        private val usedWords: MutableList<String> = mutableListOf()
        override val uiState: MutableStateFlow<GameUiState> =
            MutableStateFlow(GameUiState())
        override var userGuess: Word by mutableStateOf(Word(""))

        init {
            resetGame()
        }

        override fun resetGame() {
            usedWords.clear()
            randomWords = RandomWords(allWords)
            uiState.value = GameUiState(currentWord = randomWords.next())
            usedWords.add(uiState.value.currentWord.value)
        }

        override fun updateUserGuess(guessedWord: Word) {
            userGuess = guessedWord
        }

        override fun checkUserGuess() {
            if (userGuess.value.equals(uiState.value.currentWord.value, ignoreCase = true)) {
                val updatedScore = uiState.value.score + SCORE_INCREASE
                updateGameState(updatedScore)
            } else {
                uiState.update { currentState ->
                    currentState.copy(isGuessedWordWrong = true)
                }
            }
            updateUserGuess(Word(""))
        }

        override fun updateGameState(updatedScore: Int) {
            uiState.update { currentState ->
                GameUiState(
                    isGuessedWordWrong = false,
                    currentWord = randomWords.next(),
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc(),
                )
            }
        }

        override fun scipWord() {
            updateGameState(uiState.value.score)
            updateUserGuess(Word(""))
        }

    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GameViewModel.Base::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GameViewModel.Base() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

@JvmInline
value class Word(val value: String)

open class RandomWords(private val words: Collection<String>) : Collection<String> by words {
    private val randomWords: MutableList<String> = words.toMutableSet().toMutableList()
    private val iterator: MutableListIterator<String> = randomWords.listIterator()

    init {
        randomWords.shuffle()
    }

    fun next(): Word {
        return Word(iterator.next())
    }
}

open class ScrambledWord(private val word: Word) {
    val value: String

    init {
        var scrambled: String = word.value
        var count = 0
        while (scrambled == word.value) {
            if (word.value.isBlank() || count == 20) break
            val charArray = word.value.toCharArray()
            charArray.shuffle()
            scrambled = charArray.joinToString(separator = "")
            count++
        }
        value = scrambled
    }
}
