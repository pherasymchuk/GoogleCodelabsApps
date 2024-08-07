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
import android.util.Log
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
import java.io.IOException
import java.util.UUID

private const val TAG = "WorkerUtils"

interface StatusNotification {
    fun make()

    class Default(
        private val message: String,
        notificationManagerWrapper: NotificationManagerWrapper,
        private val notificationBuilderWrapper: NotificationBuilderWrapper,
        private val notificationConfig: NotificationConfig = NotificationConfig.Default(),
        private val channelConfig: ChannelConfig = ChannelConfig.Default(),
    ) : StatusNotification {

        private val notificationManager = notificationManagerWrapper.notificationManager()

        override fun make() {
            // Create notification channel
            channelConfig.createChannel(notificationManager)
            // Build and show the notification
            val notification = notificationConfig.buildNotification(notificationBuilderWrapper, message)
            notificationManager.notify(notificationConfig.notificationId(), notification)
        }
    }
}

interface NotificationConfig {
    fun notificationId(): Int
    fun buildNotification(
        notificationBuilderWrapper: NotificationBuilderWrapper,
        message: String,
    ): Notification

    class Default(
        private val notificationId: Int = NOTIFICATION_ID,
        private val title: CharSequence = NOTIFICATION_TITLE,
        private val priority: Int = NotificationCompat.PRIORITY_HIGH,
    ) : NotificationConfig {
        override fun notificationId() = notificationId

        override fun buildNotification(
            notificationBuilderWrapper: NotificationBuilderWrapper,
            message: String,
        ): Notification {
            return notificationBuilderWrapper.notificationBuilder()
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(priority)
                .setVibrate(LongArray(0))
                .build()
        }
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
     * @param bitmap Image to blur
     * @param blurLevel Blur level input
     * @return Blurred bitmap image
     */
    class Default(
        private val bitmap: Bitmap,
        private val blurLevel: Int,
    ) : BlurredBitmap {

        /**
         * Blurs the given Bitmap image
         * @return Blurred bitmap image
         */
        @WorkerThread
        override fun blur(): Bitmap {
            val input = Bitmap.createScaledBitmap(
                bitmap,
                bitmap.width / (blurLevel * 5),
                bitmap.height / (blurLevel * 5),
                true
            )
            return Bitmap.createScaledBitmap(input, bitmap.width, bitmap.height, true)
        }
    }
}

interface BitmapFile {
    fun write(): Uri

    class Default(
        private val applicationContext: Context,
        private val bitmap: Bitmap,
        private val name: String = "blur-filter-output-${UUID.randomUUID()}.png",
        private val outputPath: String = OUTPUT_PATH,
    ) : BitmapFile {
        /**
         * Writes bitmap to a temporary file and returns the Uri for the file
         *  @return Uri for temp file with bitmap
         */
        @Throws(FileNotFoundException::class)
        override fun write(): Uri {
            val outputDir = File(applicationContext.filesDir, outputPath)
            if (!outputDir.exists()) {
                outputDir.mkdirs() // should succeed
            }
            val outputFile = File(outputDir, name)
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(outputFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
            } finally {
                out?.let {
                    try {
                        it.close()
                    } catch (e: IOException) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            }
            return Uri.fromFile(outputFile)
        }
    }
}

