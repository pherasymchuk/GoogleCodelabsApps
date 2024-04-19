package com.example.marsphotos

import com.example.marsphotos.data.AppContainer

interface AppContainerProvider {
    fun getAppContainer(): AppContainer
}
