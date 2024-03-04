package com.herasymchuk.unscramble.ui

data class GameUiState(
    var currentWord: Word = Word(""),
    val currentScrambledWord: ScrambledWord = ScrambledWord(Word("")),
    val isUserGuessWrong: Boolean = false,
    val score: Int = 0,
    val count: Int = 1,
    val isGameOver: Boolean = false,
)
