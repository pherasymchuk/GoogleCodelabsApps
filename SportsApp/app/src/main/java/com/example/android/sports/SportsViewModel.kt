/*
 * Copyright (c) 2021 The Android Open Source Project
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

package com.example.android.sports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.sports.data.SportsRepository
import com.example.android.sports.model.Sport

abstract class SportsViewModel : ViewModel() {
    abstract val currentSport: LiveData<Sport>
    abstract val sportsData: ArrayList<Sport>

    abstract fun updateCurrentSport(sport: Sport)

    class Base : SportsViewModel() {
        override var currentSport: MutableLiveData<Sport> = MutableLiveData<Sport>()
        override var sportsData: ArrayList<Sport> = ArrayList()

        init {
            // Initialize the sports data.
            sportsData = SportsRepository.getSportsData()
            currentSport.value = sportsData[0]
        }

        override fun updateCurrentSport(sport: Sport) {
            currentSport.value = sport
        }
    }
}
