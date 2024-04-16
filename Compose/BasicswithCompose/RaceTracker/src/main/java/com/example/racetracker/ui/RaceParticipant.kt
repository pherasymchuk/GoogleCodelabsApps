/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.racetracker.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

interface Runner {
    val currentProgress: Int

    suspend fun run()
    fun reset()
}

/**
 * This class represents a state holder for race participant.
 */
class RaceParticipant(
    val name: String,
    val maxProgress: Int = 100,
    private val progressDelayMillis: Long = 50L,
    private val progressIncrement: Int = 1,
    private val initialProgress: Int = 0
) : Runner {
    init {
        require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
        require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
    }

    /**
     * Indicates the race participant's current progress
     */
    override var currentProgress by mutableIntStateOf(initialProgress)
        private set

    override suspend fun run() {
        try {
            while (currentProgress < maxProgress) {
                delay(progressDelayMillis)
                currentProgress += progressIncrement
                if (currentProgress > 100) currentProgress = 100
            }
        } catch (e: CancellationException) {
            Log.e("RaceParticipant", "$name: ${e.message}")
            throw e
        }
    }

    /**
     * Regardless of the value of [initialProgress] the reset function will reset the
     * [currentProgress] to 0
     */
    override fun reset() {
        currentProgress = 0
    }
}

/**
 * The Linear progress indicator expects progress value in the range of 0-1. This property
 * calculate the progress factor to satisfy the indicator requirements.
 */
val RaceParticipant.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()