package com.herasymchuk.unscramble.ui

data class GameUiState(
    val currentScrambledWord: ScrambledWord = ScrambledWord(Word("")),
    val isUserGuessWrong: Boolean = false,
)
