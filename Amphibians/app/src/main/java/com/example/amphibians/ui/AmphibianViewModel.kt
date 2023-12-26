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
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.Api
import kotlinx.coroutines.launch

enum class AmphibianApiStatus { LOADING, ERROR, DONE }

abstract class AmphibianViewModel : ViewModel() {

    abstract val status: LiveData<AmphibianApiStatus>
    abstract val amphibians: LiveData<List<Amphibian>>
    abstract val amphibian: LiveData<Amphibian>
    // TODO: Create a function that gets a list of amphibians from the api service and sets the
    //  status via a Coroutine
    abstract fun fetchAmphibians()
    abstract fun onAmphibianClicked(amphibian: Amphibian)

    class Base : AmphibianViewModel() {
        override val status: MutableLiveData<AmphibianApiStatus> = MutableLiveData()
        override val amphibians: MutableLiveData<List<Amphibian>> = MutableLiveData()
        override val amphibian: MutableLiveData<Amphibian> = MutableLiveData()

        override fun fetchAmphibians() {
            status.value = AmphibianApiStatus.LOADING
            viewModelScope.launch {
                try {
                    Api.Amphibians.retrofitService.getAmphibians()
                    status.value = AmphibianApiStatus.DONE
                } catch (e: Exception) {
                    status.value = AmphibianApiStatus.ERROR
                    amphibians.value = emptyList()
                }
            }
        }

        override fun onAmphibianClicked(amphibian: Amphibian) {
            this.amphibian.value = amphibian
        }
    }
}
