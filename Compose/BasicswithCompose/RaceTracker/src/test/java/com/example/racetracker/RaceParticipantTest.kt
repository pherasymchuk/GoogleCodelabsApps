package com.example.racetracker

import com.example.racetracker.ui.RaceParticipant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RaceParticipantTest {
    private lateinit var raceParticipant: RaceParticipant

    @BeforeEach
    fun initialize() {
        raceParticipant = RaceParticipant(
            name = "Test",
            maxProgress = 100,
            progressDelayMillis = 500L,
            initialProgress = 0,
            progressIncrement = 1
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Nested
    inner class ProgressUpdating {
        @Test
        fun raceParticipant_RaceStarted_ProgressUpdatedCorrectly() = runTest {
            val expectedProgress = 1
            launch { raceParticipant.run() }
            advanceTimeBy(raceParticipant.progressDelayMillis)
            runCurrent()
            assertEquals(expectedProgress, raceParticipant.currentProgress)
        }

        @Test
        fun raceParticipant_RaceFinished_ProgressUpdatedCorrectly() = runTest {
            launch { raceParticipant.run() }
            advanceTimeBy(raceParticipant.progressDelayMillis * raceParticipant.maxProgress)
            runCurrent()
            assertEquals(raceParticipant.maxProgress, raceParticipant.currentProgress)
        }
    }

    @Nested
    inner class SuccessPath

    @Nested
    inner class ErrorPath {
        @Test
        fun initialProgress_BiggerThanMaxProgress() {
            assertThrows<IllegalArgumentException>(message = "Initial progress cannot be bigger than max progress") {
                raceParticipant = RaceParticipant(name = "Dan", maxProgress = 50, initialProgress = 100)
            }
        }
    }

    @Nested
    inner class BoundaryCase {
        @Test
        fun raceParticipant_Created_NoProgress() {
            assertEquals(0, raceParticipant.currentProgress)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun raceParticipant_RaceFinished_ProgressNotHigherThanMaxProgress() = runTest {
            launch { raceParticipant.run() }
            advanceUntilIdle()
            runCurrent()
            assertEquals(raceParticipant.maxProgress, raceParticipant.currentProgress)
        }
    }
}
