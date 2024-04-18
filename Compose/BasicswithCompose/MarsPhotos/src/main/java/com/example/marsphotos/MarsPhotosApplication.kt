package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.data.AppContainer

class MarsPhotosApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer.Default()
    }
}
