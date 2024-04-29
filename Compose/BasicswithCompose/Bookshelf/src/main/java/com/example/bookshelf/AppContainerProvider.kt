package com.example.bookshelf

import com.example.bookshelf.data.di.AppContainer

interface AppContainerProvider {
    fun getAppContainer(): AppContainer
}
