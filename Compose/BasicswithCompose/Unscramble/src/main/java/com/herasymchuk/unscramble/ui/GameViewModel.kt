package com.herasymchuk.unscramble.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herasymchuk.unscramble.model.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class GameViewModel<T> : ViewModel() {
    abstract val uiState: StateFlow<T>

    abstract fun resetGame()

    class Base : GameViewModel<GameUiState>() {
        override val uiState: MutableStateFlow<GameUiState> = MutableStateFlow(GameUiState())
        private var randomWords: RandomWords = RandomWords(allWords)

        private var currentWord: Word = Word("")
        private val usedWords: MutableList<Word> = mutableListOf()

        init {
            resetGame()
        }

        override fun resetGame() {
            usedWords.clear()
            randomWords = RandomWords(allWords)
            currentWord = randomWords.next()
            val scrambled = ScrambledWord(currentWord)
            uiState.value = GameUiState(currentScrambledWord = scrambled)
        }

        class Factory : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(Base::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return Base() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
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
                if (word.value.isBlank() || count == 50) break
                val charArray = word.value.toCharArray()
                charArray.shuffle()
                scrambled = String(charArray)
                count++
            }
            value = scrambled
        }
    }
