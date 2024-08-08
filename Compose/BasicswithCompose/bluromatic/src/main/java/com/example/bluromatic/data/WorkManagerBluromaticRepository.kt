/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluromatic.data

import android.content.Context
import android.net.Uri
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.bluromatic.ImageUri
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import com.example.bluromatic.workers.SaveImageToFileWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {

    override val outputWorkInfo: Flow<WorkInfo?> = MutableStateFlow(null)
    private val workManager = WorkManager.getInstance(context)
    private val imageUri: Uri = ImageUri.Drawable(context).uri()

    /**
     * Create the WorkRequests to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    override fun applyBlur(blurLevel: Int) {
        val cleanupRequest = OneTimeWorkRequest.Companion.from(CleanupWorker::class.java)
        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(
                workDataOf(
                    KEY_IMAGE_URI to imageUri.toString(),
                    KEY_BLUR_LEVEL to blurLevel
                )
            ).build()
        val saveRequest = OneTimeWorkRequestBuilder<SaveImageToFileWorker>().build()

        workManager.beginWith(cleanupRequest)
            .then(blurRequest)
            .then(saveRequest)
            .enqueue()
    }

    /**
     * Cancel any ongoing WorkRequests
     * */
    override fun cancelWork() {
        TODO("Not yet implemented")
    }
}
