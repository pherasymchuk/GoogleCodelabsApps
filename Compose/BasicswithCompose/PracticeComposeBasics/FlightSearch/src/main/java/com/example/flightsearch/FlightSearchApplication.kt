package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.data.di.AppContainer

class FlightSearchApplication : Application(), AppContainerProvider {
    override val appContainer: AppContainer by lazy { AppContainer.Default(this) }

}

interface AppContainerProvider {
    val appContainer: AppContainer
}
