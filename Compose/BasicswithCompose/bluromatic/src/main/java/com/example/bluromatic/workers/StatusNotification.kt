/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluromatic.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.example.bluromatic.CHANNEL_ID
import com.example.bluromatic.NOTIFICATION_ID
import com.example.bluromatic.NOTIFICATION_TITLE
import com.example.bluromatic.R
import com.example.bluromatic.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.bluromatic.VERBOSE_NOTIFICATION_CHANNEL_NAME
import com.example.bluromatic.wrappers.NotificationBuilderWrapper
import com.example.bluromatic.wrappers.NotificationManagerWrapper

interface StatusNotification {
    fun show()
    fun cancel()

    class Default(
        private val message: String,
        notificationManagerWrapper: NotificationManagerWrapper,
        notificationBuilderWrapper: NotificationBuilderWrapper,
        private val notificationConfig: NotificationConfig = NotificationConfig.Default(notificationBuilderWrapper),
        private val channelConfig: ChannelConfig = ChannelConfig.Default(),
    ) : StatusNotification {

        private val notificationManager = notificationManagerWrapper.notificationManager()

        override fun show() {
            channelConfig.createChannel(notificationManager)
            val notification = notificationConfig.buildNotification(message)
            notificationManager.notify(notificationConfig.notificationId(), notification)
        }

        override fun cancel() {
            notificationManager.cancel(notificationConfig.notificationId())
        }
    }
}

interface NotificationConfig {
    fun notificationId(): Int
    fun buildNotification(message: String): Notification

    class Default(
        private val notificationBuilderWrapper: NotificationBuilderWrapper,
        private val notificationId: Int = NOTIFICATION_ID,
        private val title: CharSequence = NOTIFICATION_TITLE,
        private val priority: Int = NotificationCompat.PRIORITY_HIGH,
        private val smallIcon: Int = R.drawable.ic_launcher_foreground,
    ) : NotificationConfig {
        override fun notificationId() = notificationId

        override fun buildNotification(
            message: String,
        ): Notification = notificationBuilderWrapper.notificationBuilder()
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(priority)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .build()
    }
}

interface ChannelConfig {
    fun createChannel(notificationManager: NotificationManager)

    class Default(
        private val channelId: String = CHANNEL_ID,
        private val name: CharSequence = VERBOSE_NOTIFICATION_CHANNEL_NAME,
        private val description: String = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION,
        private val importance: Int = NotificationManager.IMPORTANCE_HIGH,
    ) : ChannelConfig {

        override fun createChannel(notificationManager: NotificationManager) {
            val channel = NotificationChannel(channelId, name, importance).also { channel: NotificationChannel ->
                channel.description = description
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

}

