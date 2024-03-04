package com.herasymchuk.unscramble.ui

import androidx.lifecycle.ViewModel
import com.herasymchuk.unscramble.model.MAX_NO_OF_WORDS
import com.herasymchuk.unscramble.model.SCORE_INCREASE
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class GameViewModelTest {
    private val vm = GameViewModel.Base()

    // Success path
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var gameUiState: GameUiState = vm.uiState.value
        vm.updateUserGuess(gameUiState.currentWord.value)
        vm.checkUserGuess()
        gameUiState = vm.uiState.value

        assertFalse(
            "Wrong guess should be false if the user enters correct word",
            gameUiState.isUserGuessWrong
        )
        assertEquals(SCORE_INCREASE, gameUiState.score)
    }

    // Error path
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // does not exist in the list of words
        val incorrectWord = "and"
        vm.updateUserGuess(incorrectWord)
        vm.checkUserGuess()

        val gameUiState: GameUiState = vm.uiState.value
        assertEquals("score should not be changed", 0, gameUiState.score)
        assertTrue(
            "checkUserGuess should make user guess wrong if input is invalid",
            gameUiState.isUserGuessWrong
        )
    }

    // Boundary case (initial state)
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState: GameUiState = vm.uiState.value
        assertNotEquals(
            "Scrambled word should not be the same as correct word",
            gameUiState.currentScrambledWord.value,
            gameUiState.currentWord.value
        )
        assertTrue(gameUiState.count == 1)
        assertTrue(gameUiState.score == 0)
        assertFalse(gameUiState.isUserGuessWrong)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var exprectedScore: Int = 0
        var gameUiState: GameUiState = vm.uiState.value
        var correctWord: Word = gameUiState.currentWord

        repeat(MAX_NO_OF_WORDS) {
            exprectedScore += SCORE_INCREASE
            vm.updateUserGuess(correctWord.value)
            vm.checkUserGuess()
            gameUiState = vm.uiState.value
            correctWord = gameUiState.currentWord
            assertEquals(exprectedScore, gameUiState.score)
        }

        assertEquals(exprectedScore, gameUiState.score)
        assertTrue(gameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_ScippedWord_ScoreUnchangedAndWordCountIncreased() {
        var gameUiState: GameUiState = vm.uiState.value
        val expectedScore = SCORE_INCREASE
        val expectedCount = MAX_NO_OF_WORDS

        vm.updateUserGuess(gameUiState.currentWord.value)
        vm.checkUserGuess()

        repeat(MAX_NO_OF_WORDS - 1) {
            vm.scipWord()
        }

        gameUiState = vm.uiState.value

        assertTrue(
            "Game score should be $SCORE_INCREASE but was ${gameUiState.score}",
            gameUiState.score == expectedScore
        )
        assertTrue(expectedCount == expectedCount)
    }

    @Test
    fun viewModelFactory_createViewModel_BaseInstanceCreated() {
        val gameVMBase: GameViewModel<*> =
            GameViewModel.BaseFactory.create(GameViewModel::class.java)
        val isBaseInstance: Boolean = gameVMBase is GameViewModel.Base
        assertTrue(isBaseInstance)
    }

    @Test
    fun viewModelFactory_PassWrongViewModelClass_ThrowException() {
        assertThrows(IllegalArgumentException::class.java) {
            GameViewModel.BaseFactory.create(FakeViewModel::class.java)
        }
    }
}

class FakeViewModel : ViewModel()
