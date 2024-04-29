package com.example.bookshelf

import android.app.Application
import com.example.bookshelf.data.di.AppContainer

class BookshelfApplication : Application(), AppContainerProvider {
    private val appContainer: AppContainer = AppContainer.Default()
    override fun getAppContainer(): AppContainer = appContainer
}
