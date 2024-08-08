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
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import com.example.bluromatic.CHANNEL_ID
import com.example.bluromatic.NOTIFICATION_ID
import com.example.bluromatic.NOTIFICATION_TITLE
import com.example.bluromatic.OUTPUT_PATH
import com.example.bluromatic.R
import com.example.bluromatic.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.bluromatic.VERBOSE_NOTIFICATION_CHANNEL_NAME
import com.example.bluromatic.wrappers.NotificationBuilderWrapper
import com.example.bluromatic.wrappers.NotificationManagerWrapper
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.UUID

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
            .setSilent(true)
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

interface BlurredBitmap {
    @WorkerThread
    fun blur(): Bitmap

    /**
     * Creates an instance of [BlurredBitmap]
     * @param original Image to blur
     * @param blurRadius Blur radius (higher value means more blur)
     * @return Blurred bitmap image
     */
    class Default(
        private val original: Bitmap,
        private val blurRadius: Int,
    ) : BlurredBitmap {

        /**
         * Blurs the given Bitmap image using a
         * simple scaling technique.
         *
         * @return Blurred bitmap image
         */
        @WorkerThread
        override fun blur(): Bitmap {
            val scaleFactor = blurRadius * 5
            val scaledWidth = original.width / scaleFactor
            val scaledHeight = original.height / scaleFactor

            val input = Bitmap.createScaledBitmap(
                original,
                scaledWidth,
                scaledHeight,
                true
            )

            return Bitmap.createScaledBitmap(input, original.width, original.height, true)
        }
    }
}

interface WriteBitmapFile {
    fun write(): Uri

    class Default(
        private val context: Context,
        private val bitmap: Bitmap,
        private val name: String = "blur-filter-output-${UUID.randomUUID()}.png",
        private val outputDir: String = OUTPUT_PATH,
    ) : WriteBitmapFile {
        /**
         * Writes bitmap to a temporary file and returns the Uri for the file
         *  @return Uri for temp file with bitmap
         */
        @Throws(FileNotFoundException::class)
        override fun write(): Uri {
            val outputDir = File(context.filesDir, outputDir)
            if (!outputDir.exists()) {
                outputDir.mkdirs() // should succeed
            }
            val outputFile = File(outputDir, name)

            FileOutputStream(outputFile).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, it)
            }

            return Uri.fromFile(outputFile)
        }
    }
}

