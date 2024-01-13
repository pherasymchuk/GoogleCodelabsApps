/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.background

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.background.workers.BlurWorker

abstract class BlurViewModel(private val application: Application) : ViewModel() {
    abstract val workManager: WorkManager
    internal abstract val imageUri: Uri?
    internal abstract val outputUri: Uri?

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal abstract fun applyBlur(blurLevel: Int)
    protected abstract fun uriOrNull(uriString: String?): Uri?
    protected abstract fun getImageUri(context: Context): Uri
    abstract fun setOutputUri(outputImageUri: String?)

    protected class Base(application: Application) : BlurViewModel(application) {
        override val workManager: WorkManager = WorkManager.getInstance(application)
        override var imageUri: Uri? = null
        override var outputUri: Uri? = null

        init {
            imageUri = getImageUri(application.applicationContext)
        }

        override fun applyBlur(blurLevel: Int) {
            workManager.enqueue(OneTimeWorkRequest.from(BlurWorker::class.java))
        }

        override fun uriOrNull(uriString: String?): Uri? {
            return if (!uriString.isNullOrEmpty()) {
                Uri.parse(uriString)
            } else {
                null
            }
        }

        override fun getImageUri(context: Context): Uri {
            val resources = context.resources

            val imageUri = Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.android_cupcake))
                .appendPath(resources.getResourceTypeName(R.drawable.android_cupcake))
                .appendPath(resources.getResourceEntryName(R.drawable.android_cupcake))
                .build()

            return imageUri
        }

        override fun setOutputUri(outputImageUri: String?) {
            outputUri = uriOrNull(outputImageUri)
        }
    }

    class BlurViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BlurViewModel.Base::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return Base(application) as T
            }
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
