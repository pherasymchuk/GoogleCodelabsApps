package com.example.amphibians

import android.app.Application
import com.example.amphibians.data.di.AppContainer

class AmphibiansApplication : Application(), AppContainerProvider {
    private val appContainer: AppContainer = AppContainer.Default

    override fun getAppContainer(): AppContainer {
        return appContainer
    }
}
