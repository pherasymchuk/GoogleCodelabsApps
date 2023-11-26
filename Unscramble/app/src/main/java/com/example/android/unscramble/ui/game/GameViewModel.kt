package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val TAG = "GameFragment"

abstract class GameViewModel : ViewModel() {
    abstract val score: LiveData<Int>
    abstract val currentWordCount: LiveData<Int>
    abstract val currentWord: String
    abstract val currentScrambledWord: LiveData<String>
    abstract val wordList: List<String>

    abstract fun nextWord(): Boolean
    abstract fun isUserWordCorrect(word: String): Boolean
    abstract fun increaseScore()
    abstract fun reinitializeData()

    /**
     * Never use this class directly
     */
    class Base : GameViewModel() {
        override val score: MutableLiveData<Int> = MutableLiveData(0)
        override val currentWordCount: MutableLiveData<Int> = MutableLiveData(0)
        override var currentScrambledWord: MutableLiveData<String> = MutableLiveData<String>()
        override var wordList: MutableList<String> = mutableListOf()
        override lateinit var currentWord: String

        init {
            nextWord()
        }

        /**
         * Update [currentWord] and [currentScrambledWord] with the next word.
         */
        override fun nextWord(): Boolean {
            if (currentWordCount.value!! >= MAX_NO_OF_WORDS) return false

            currentWord = getAllWordsList().random()
            val tempWord = currentWord.toCharArray()
            tempWord.shuffle()

            while (tempWord.joinToString().equals(currentWord, true)) tempWord.shuffle()

            if (wordList.contains(currentWord)) {
                nextWord()
            } else {
                currentScrambledWord.value = String(tempWord)
                currentWordCount.value = currentWordCount.value?.inc()
                wordList.add(currentWord)
            }
            return true
        }

        override fun increaseScore() {
            score.value = score.value?.plus(SCORE_INCREASE)
        }

        override fun isUserWordCorrect(word: String): Boolean {
            if (word.trim().equals(currentWord, true)) {
                increaseScore()
                return true
            }
            return false
        }

        /**
         * Re-initializes the game data to restart the game.
         */
        override fun reinitializeData() {
            score.value = 0
            currentWordCount.value = 0
            wordList.clear()
            nextWord()
        }
    }
}
