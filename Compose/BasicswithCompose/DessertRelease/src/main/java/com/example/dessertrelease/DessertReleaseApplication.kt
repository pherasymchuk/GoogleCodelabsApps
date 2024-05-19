package com.example.dessertrelease

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dessertrelease.data.local.UserPreferencesRepository

class DessertReleaseApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository
        private set

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }

    companion object {
        private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = LAYOUT_PREFERENCES_NAME
        )
    }
}
