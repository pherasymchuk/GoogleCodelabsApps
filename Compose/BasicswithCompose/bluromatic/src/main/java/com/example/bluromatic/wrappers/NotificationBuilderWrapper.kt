package com.example.bluromatic.wrappers

import androidx.core.app.NotificationCompat

interface NotificationBuilderWrapper {
    fun notificationBuilder(): NotificationCompat.Builder

    class Default(
        private val notificationBuilder: NotificationCompat.Builder,
    ) : NotificationBuilderWrapper {
        override fun notificationBuilder(): NotificationCompat.Builder = notificationBuilder
    }
}
