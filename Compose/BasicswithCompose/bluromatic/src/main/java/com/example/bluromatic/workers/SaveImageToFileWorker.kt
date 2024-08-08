package com.example.bluromatic.workers

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.bluromatic.CHANNEL_ID
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.R
import com.example.bluromatic.wrappers.NotificationBuilderWrapper
import com.example.bluromatic.wrappers.NotificationManagerWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SaveImageToFileWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    private val notificationManager = NotificationManagerWrapper.Default(
        notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
    )
    private val notificationBuilder = NotificationBuilderWrapper.Default(
        notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
    )
    private val statusNotification = StatusNotification.Default(
        message = applicationContext.getString(R.string.saving_image),
        notificationManager,
        notificationBuilder
    )
    private val imageUri = inputData.getString(KEY_IMAGE_URI)
    private val resolver = applicationContext.contentResolver
    private val title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )

    override suspend fun doWork(): Result {
        statusNotification.show()

        return withContext(Dispatchers.IO) {
            delay(DELAY_TIME_MILLIS)

            try {
                val bitmap = BitmapFactory.decodeStream(
                    applicationContext.contentResolver.openInputStream(Uri.parse(imageUri))
                )
                val imageUrl = MediaStore.Images.Media.insertImage(
                    resolver, bitmap, title, dateFormatter.format(Date())
                )
                if (!imageUrl.isNullOrEmpty()) {
                    val output = workDataOf(KEY_IMAGE_URI to imageUrl)

                    Result.success(output)
                } else {
                    Log.e(
                        "Logs",
                        applicationContext.resources.getString(R.string.writing_to_mediaStore_failed)
                    )
                    Result.failure()
                }
            } catch (e: Exception) {
                Log.e(
                    "Logs",
                    applicationContext.resources.getString(R.string.error_saving_image),
                    e
                )
                Result.failure()
            } finally {
                statusNotification.cancel()
            }
        }
    }
}
