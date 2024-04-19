package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.di.AppContainer

class MarsPhotosApplication : Application(), AppContainerProvider {
    private lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer.Default()
    }

    override fun getAppContainer(): AppContainer {
        return appContainer
    }
}
