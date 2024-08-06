package com.example.bluromatic.workers

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
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
    private val context: Context,
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
        val default = StatusNotification.Default(
            message = "Blurring image",
            context = applicationContext,
            notificationManagerWrapper = notificationManagerWrapper,
            notificationBuilderWrapper = notificationBuilderWrapper
        )
        val statusNotification = default
        statusNotification.make()

        return withContext(Dispatchers.IO) {
            try {
                // Simulate delay
                delay(DELAY_TIME_MILLIS)
                val picture = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.android_cupcake
                )

                val output = BlurredBitmap.Default(
                    bitmap = picture,
                    blurLevel = 1
                ).blur()

                val outputUri = BitmapFile.Default(
                    applicationContext,
                    output
                ).write()

                StatusNotification.Default(
                    message = "Output is $outputUri",
                    context = applicationContext,
                    notificationManagerWrapper = notificationManagerWrapper,
                    notificationBuilderWrapper = notificationBuilderWrapper
                )

                Result.success()
            } catch (throwable: Throwable) {
                Log.e(
                    tag,
                    applicationContext.resources.getString(R.string.error_applying_blur),
                    throwable
                )
                Result.failure()
            }
        }
    }
}
