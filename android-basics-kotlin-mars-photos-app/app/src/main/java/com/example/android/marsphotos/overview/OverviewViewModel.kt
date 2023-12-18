/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphotos.network.MarsApi
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
abstract class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    abstract val status: LiveData<String>

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    protected abstract fun getMarsPhotos()

    class Base : OverviewViewModel() {
        override val status = MutableLiveData<String>()

        /**
         * Call getMarsPhotos() on init so we can display status immediately.
         */
        init {
            getMarsPhotos()
        }

        override fun getMarsPhotos() {
            status.value = "Set the Mars API status response here!"
            viewModelScope.launch {
                val listResult: String = MarsApi.retrofitService.getPhotos()
                status.value = listResult
            }
        }
    }
}
