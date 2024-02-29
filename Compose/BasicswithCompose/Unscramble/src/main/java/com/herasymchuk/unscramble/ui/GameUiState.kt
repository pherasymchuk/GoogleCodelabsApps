package com.herasymchuk.unscramble.ui

data class GameUiState(
    val currentScrambledWord: ScrambledWord = ScrambledWord(Word("")),
)
