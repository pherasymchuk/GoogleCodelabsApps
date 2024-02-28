package com.herasymchuk.unscramble.ui

data class GameUiState(
    val currentWord: Word = Word(""),
    val currentScrambledWord: ScrambledWord = ScrambledWord(currentWord),
    val currentWordCount: Int = 1,
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
)
