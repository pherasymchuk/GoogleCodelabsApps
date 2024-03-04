package com.herasymchuk.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herasymchuk.unscramble.model.MAX_NO_OF_WORDS
import com.herasymchuk.unscramble.model.SCORE_INCREASE
import com.herasymchuk.unscramble.model.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class GameViewModel<T> : ViewModel() {
    abstract val uiState: StateFlow<T>
    abstract val userGuess: Word

    abstract fun resetGame()
    abstract fun updateUserGuess(guessedWord: String)

    /**
     * If user guessed word correctly - increases score, count and moves to the next word
     */
    abstract fun checkUserGuess()
    abstract fun scipWord()

    class Base : GameViewModel<GameUiState>() {
        override val uiState: MutableStateFlow<GameUiState> = MutableStateFlow(GameUiState())
        private var randomWords: RandomWords = RandomWords(allWords)
        private val usedWords: MutableList<Word> = mutableListOf()
        override var userGuess: Word by mutableStateOf(Word(""))

        init {
            resetGame()
        }

        override fun resetGame() {
            usedWords.clear()
            randomWords = RandomWords(allWords)
            val currentWord = randomWords.next()
            usedWords.add(currentWord)
            val scrambled = ScrambledWord(currentWord)
            uiState.update { it.copy(currentWord = currentWord, currentScrambledWord = scrambled) }
        }

        override fun updateUserGuess(guessedWord: String) {
            userGuess = Word(guessedWord)
        }

        override fun checkUserGuess() {
            if (userGuess.value.equals(uiState.value.currentWord.value, ignoreCase = true)) {
                val updatedScore: Int = uiState.value.score + SCORE_INCREASE
                nextWord()
                updateGameScore(updatedScore)
            } else {
                uiState.value = uiState.value.copy(isUserGuessWrong = true)
            }
            // Reset user guess
            updateUserGuess("")
        }

        override fun scipWord() {
            nextWord()
            updateGameScore(uiState.value.score)
            updateUserGuess("")
        }

        private fun updateGameScore(newScore: Int) {
            val isLastRound = usedWords.size == MAX_NO_OF_WORDS + 1
            if (isLastRound) {
                uiState.update {
                    it.copy(
                        isUserGuessWrong = false,
                        score = newScore,
                        isGameOver = true
                    )
                }
            } else {
                uiState.update {
                    it.copy(
                        isUserGuessWrong = false,
                        score = newScore,
                        count = it.count.inc()
                    )
                }
            }

        }

        private fun nextWord() {
            val currentWord: Word = randomWords.next()
            usedWords.add(currentWord)
            uiState.value = uiState.value.copy(
                currentWord = currentWord, currentScrambledWord = ScrambledWord(currentWord)
            )
        }

    }

    object BaseFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(Base::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return Base() as T
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

open class ScrambledWord(word: Word) {
    val value: String

    init {
        var scrambled: String = word.value
        var count = 0
        while (scrambled == word.value) {
            if (word.value.isBlank() || count == 50) break
            val charArray = word.value.toCharArray()
            charArray.shuffle()
            scrambled = String(charArray)
            count++
        }
        value = scrambled
    }
}
