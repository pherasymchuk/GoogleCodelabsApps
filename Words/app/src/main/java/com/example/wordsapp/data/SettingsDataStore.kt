package com.example.wordsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsDataStore(context: Context) {
    private val isLinearLayoutManager = booleanPreferencesKey("is_linear_layout_manager")
    val preferenceFlow: Flow<Boolean> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[isLinearLayoutManager] ?: true
        }

    suspend fun saveLayoutToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[this.isLinearLayoutManager] = isLinearLayoutManager
        }
    }

    private companion object {
        private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = LAYOUT_PREFERENCES_NAME
        )
    }
}
