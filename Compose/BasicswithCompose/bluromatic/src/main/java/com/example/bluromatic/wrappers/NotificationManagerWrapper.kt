package com.example.bluromatic.wrappers

import android.app.NotificationManager

interface NotificationManagerWrapper {
    fun notificationManager(): NotificationManager

    class Default(
        private val notificationManager: NotificationManager,
    ) : NotificationManagerWrapper {
        override fun notificationManager(): NotificationManager {
            return notificationManager
        }
    }
}
