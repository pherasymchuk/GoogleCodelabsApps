package com.example.amphibians

import com.example.amphibians.data.di.AppContainer

interface AppContainerProvider {
    fun getAppContainer(): AppContainer
}
