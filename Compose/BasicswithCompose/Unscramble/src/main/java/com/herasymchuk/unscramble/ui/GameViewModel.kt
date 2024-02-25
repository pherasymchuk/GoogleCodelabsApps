package com.herasymchuk.unscramble.ui

import androidx.lifecycle.ViewModel
import com.herasymchuk.unscramble.model.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class GameViewModel<T> : ViewModel() {
    abstract val uiState: StateFlow<T>

    class Base : GameViewModel<GameUiState>() {
        private var currentWord: String = ""
        override val uiState: MutableStateFlow<GameUiState> = MutableStateFlow(GameUiState())
    }
}

open class Word(open val value: String)

open class RandomWord(private val words: Collection<String>) : Word(words.random()) {
    override val value: String = words.random()
}

open class ShuffledWord(word: Word) : Word(word.value) {

    override var value: String = ""

    init {
        var currentWord = ShuffledWord(RandomWord(allWords))
    }
}
