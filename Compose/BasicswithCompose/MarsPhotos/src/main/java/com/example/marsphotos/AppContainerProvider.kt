package com.example.marsphotos

import com.example.marsphotos.di.AppContainer

interface AppContainerProvider {
    fun getAppContainer(): AppContainer
}
