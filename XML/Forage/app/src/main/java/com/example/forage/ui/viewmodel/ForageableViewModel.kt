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
package com.example.forage.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.forage.data.ForageableDao
import com.example.forage.model.Forageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Shared [ViewModel] to provide data to the [ForageableListFragment], [ForageableDetailFragment],
 * and [AddForageableFragment] and allow for interaction the the [ForageableDao]
 */
abstract class ForageableViewModel(private val forageableDao: ForageableDao) : ViewModel() {
    abstract val forageables: LiveData<List<Forageable>>

    abstract fun getForageable(id: Long): LiveData<Forageable>
    abstract fun addForageable(
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String,
    )

    abstract fun updateForageable(
        id: Long,
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String,
    )

    abstract fun deleteForageable(forageable: Forageable)
    abstract fun isValidEntry(name: String, address: String): Boolean

    class Base(
        private val forageableDao: ForageableDao,
    ) : ForageableViewModel(forageableDao) {
        override val forageables: LiveData<List<Forageable>> = forageableDao.getAll().asLiveData()

        override fun getForageable(id: Long): LiveData<Forageable> =
            forageableDao.getById(id).asLiveData()

        override fun addForageable(
            name: String,
            address: String,
            inSeason: Boolean,
            notes: String,
        ) {
            val forageable = Forageable(
                name = name,
                address = address,
                inSeason = inSeason,
                notes = notes
            )

            viewModelScope.launch(Dispatchers.IO) {
                forageableDao.insert(forageable)
            }
        }

        override fun updateForageable(
            id: Long,
            name: String,
            address: String,
            inSeason: Boolean,
            notes: String,
        ) {
            val forageable = Forageable(
                id = id,
                name = name,
                address = address,
                inSeason = inSeason,
                notes = notes
            )
            viewModelScope.launch(Dispatchers.IO) {
                forageableDao.update(forageable)
            }
        }

        override fun deleteForageable(forageable: Forageable) {
            viewModelScope.launch(Dispatchers.IO) {
                forageableDao.delete(forageable)
            }
        }

        override fun isValidEntry(name: String, address: String): Boolean {
            return name.isNotBlank() && address.isNotBlank()
        }
    }
}

class ForageableViewModelFactory(private val forageableDao: ForageableDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (ForageableViewModel.Base::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return ForageableViewModel.Base(forageableDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
