package com.example.bluromatic

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import com.example.bluromatic.data.repository.WorkManagerBluromaticRepository.Companion.KEY_IMAGE_URI
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import com.example.bluromatic.workers.SaveImageToFileWorker
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WorkerInstrumentationTest {
    private lateinit var context: Context
    private val mockUriInput =
        workDataOf(KEY_IMAGE_URI to "android.resource://com.example.bluromatic/drawable/android_cupcake")

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun cleanUpWorker_doWork_resultSuccess() {
        val worker = TestListenableWorkerBuilder<CleanupWorker>(context)
            .build()
        runBlocking {
            val result = worker.doWork()
            assertTrue(result is ListenableWorker.Result.Success)
        }
    }

    @Test
    fun blurWorker_doWork_resultSuccess() {
        val worker = TestListenableWorkerBuilder<BlurWorker>(context)
            .setInputData(mockUriInput)
            .build()
        runBlocking {
            val result: ListenableWorker.Result = worker.doWork()
            val resultUri: String? = result.outputData.getString(KEY_IMAGE_URI)
            assertTrue((result is ListenableWorker.Result.Success) && resultUri != null)
            assertTrue(result.outputData.keyValueMap.containsKey(KEY_IMAGE_URI))
            assertTrue(
                "The resultUri is not a valid content URI",
                resultUri?.startsWith("file:///data/user/0/com.example.bluromatic/files/blur_filter_outputs/blur-filter-output-")
                    ?: false
            )
        }
    }

    @Test
    fun saveImageToFileWorker_doWork_resultSuccess() {
        val worker = TestListenableWorkerBuilder<SaveImageToFileWorker>(context)
            .setInputData(mockUriInput)
            .build()
        runBlocking {
            val result = worker.doWork()
            val resultUri = result.outputData.getString(KEY_IMAGE_URI)
            assertTrue(result is ListenableWorker.Result.Success)
            assertTrue(result.outputData.keyValueMap.containsKey(KEY_IMAGE_URI))
            assertTrue(resultUri?.startsWith("context://media/external/images/media/") ?: false)
        }
    }
}
