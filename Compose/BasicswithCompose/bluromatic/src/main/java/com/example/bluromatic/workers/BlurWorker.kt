package com.example.bluromatic.workers

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bluromatic.CHANNEL_ID
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.R
import com.example.bluromatic.wrappers.NotificationBuilderWrapper
import com.example.bluromatic.wrappers.NotificationManagerWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class BlurWorker(
    private val tag: String = "BlurWorker",
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val notificationManagerWrapper = NotificationManagerWrapper.Default(
            notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        )
        val notificationBuilderWrapper = NotificationBuilderWrapper.Default(
            notificationBuilder = NotificationCompat.Builder(
                applicationContext,
                CHANNEL_ID
            )
        )
        StatusNotification.Default(
            message = "Blurring image",
            notificationManagerWrapper = notificationManagerWrapper,
            notificationBuilderWrapper = notificationBuilderWrapper
        ).make()

        return withContext(Dispatchers.IO) {

            return@withContext try {
                // Simulate delay
                delay(DELAY_TIME_MILLIS)

                val picture: Bitmap = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.android_cupcake
                )

                val output: Bitmap = BlurredBitmap.Default(
                    bitmap = picture,
                    blurLevel = 1
                ).blur()

                val outputUri: Uri = BitmapFile.Default(
                    applicationContext,
                    output
                ).write()

                StatusNotification.Default(
                    message = "Output is $outputUri",
                    notificationManagerWrapper = notificationManagerWrapper,
                    notificationBuilderWrapper = notificationBuilderWrapper
                ).make()

                Result.success()

            } catch (throwable: Throwable) {
                Log.e(tag, applicationContext.resources.getString(R.string.error_applying_blur), throwable)

                Result.failure()
            }
        }
    }
}
