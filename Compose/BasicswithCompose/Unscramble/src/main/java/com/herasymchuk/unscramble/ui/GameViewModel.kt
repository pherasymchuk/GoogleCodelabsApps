package com.herasymchuk.unscramble.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class GameViewModel<T> : ViewModel() {

    class Base : GameViewModel<GameUiState>() {


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
}
