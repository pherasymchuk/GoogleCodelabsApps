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

package com.example.bluromatic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkInfo
import com.example.bluromatic.BluromaticApplication
import com.example.bluromatic.data.blur.BlurAmount
import com.example.bluromatic.data.blur.BlurAmountData
import com.example.bluromatic.data.repository.BluromaticRepository
import com.example.bluromatic.data.repository.WorkManagerBluromaticRepository.Companion.KEY_IMAGE_URI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * [BlurViewModel] starts and stops the WorkManger and applies blur to the image. Also updates the
 * visibility states of the buttons depending on the states of the WorkManger.
 */
abstract class BlurViewModel : ViewModel() {
    abstract val uiState: StateFlow<UiState>
    abstract fun applyBlur(blurLevel: Int)
    abstract fun cancelWork()
    abstract fun setImageUri(imageUri: String)

    class Default(
        private val bluromaticRepository: BluromaticRepository,
        defaultImageUri: String,
    ) : BlurViewModel() {

        override val uiState: MutableStateFlow<UiState.Default> = MutableStateFlow(
            UiState.Default(
                loadingStatus = LoadingStatus.Idle,
                blurAmount = BlurAmountData.blurAmount,
                selectedImgUri = defaultImageUri
            )
        )

        init {
            viewModelScope.launch {
                bluromaticRepository.outputWorkInfo
                    .map { info: WorkInfo ->
                        val outputImageUri = info.outputData.getString(KEY_IMAGE_URI)
                        when {
                            info.state.isFinished && !outputImageUri.isNullOrEmpty() ->
                                LoadingStatus.Complete(outputUri = outputImageUri)

                            info.state == WorkInfo.State.CANCELLED -> LoadingStatus.Idle

                            else -> LoadingStatus.Loading
                        }
                    }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000),
                        initialValue = LoadingStatus.Idle
                    ).collect { newLoadingStatus: LoadingStatus ->
                        uiState.update { it.copy(loadingStatus = newLoadingStatus) }
                    }
            }
        }

        /**
         * Call the method from repository to create the WorkRequest to apply the blur
         * and save the resulting image
         * @param blurLevel The amount to blur the image
         */
        override fun applyBlur(blurLevel: Int) {
            bluromaticRepository.applyBlur(blurLevel, uiState.value.selectedImgUri)
        }

        override fun cancelWork() {
            bluromaticRepository.cancelWork()
        }

        override fun setImageUri(imageUri: String) {
            uiState.update { it.copy(selectedImgUri = imageUri) }
        }
    }

    interface UiState {
        val loadingStatus: LoadingStatus
        val blurAmount: List<BlurAmount>
        val selectedImgUri: String

        data class Default(
            override val loadingStatus: LoadingStatus,
            override val blurAmount: List<BlurAmount>,
            override val selectedImgUri: String,
        ) : UiState
    }

    interface LoadingStatus {
        object Idle : LoadingStatus
        object Loading : LoadingStatus
        data class Complete(val outputUri: String) : LoadingStatus
    }

    /**
     * Factory for [BlurViewModel] that takes [BluromaticRepository] as a dependency
     */
    companion object {
        fun provideFactory(
            defaultImageUri: String,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val bluromaticRepository =
                    (this[APPLICATION_KEY] as BluromaticApplication).container.bluromaticRepository
                Default(bluromaticRepository, defaultImageUri)
            }
        }
    }
}
