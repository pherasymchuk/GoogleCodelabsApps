package com.example.bluromatic.workers

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.bluromatic.CHANNEL_ID
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.R
import com.example.bluromatic.data.WriteBitmapFile
import com.example.bluromatic.data.blur.BlurredBitmap
import com.example.bluromatic.data.repository.WorkManagerBluromaticRepository.Companion.KEY_IMAGE_URI
import com.example.bluromatic.wrappers.NotificationBuilderWrapper
import com.example.bluromatic.wrappers.NotificationManagerWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class BlurWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    private val tag: String = "BlurWorker"

    private val notificationManagerWrapper = NotificationManagerWrapper.Default(
        notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
    )
    private val notificationBuilderWrapper = NotificationBuilderWrapper.Default(
        notificationBuilder = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
    )

    override suspend fun doWork(): Result {
        val resourceUri: String? = inputData.getString(KEY_IMAGE_URI)
        val blurLevel: Int = inputData.getInt(KEY_BLUR_LEVEL, 1)

        StatusNotification.Default(
            message = "Blurring image",
            notificationManagerWrapper = notificationManagerWrapper,
            notificationBuilderWrapper = notificationBuilderWrapper
        ).show()

        return withContext(Dispatchers.IO) {
            try {
                require(!resourceUri.isNullOrBlank()) {
                    val errorMessage = "Invalid input uri: $resourceUri"
                    Log.e("Logs", errorMessage)
                    errorMessage
                }

                delay(DELAY_TIME_MILLIS)

                val resolver = applicationContext.contentResolver
                val picture = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri))
                )

                val blurredBitmap: Bitmap = BlurredBitmap.Default(
                    original = picture,
                    blurRadius = blurLevel
                ).blur()

                val outputUri: Uri = WriteBitmapFile.Default(
                    applicationContext,
                    blurredBitmap
                ).write()

                StatusNotification.Default(
                    message = "Output is $outputUri",
                    notificationManagerWrapper = notificationManagerWrapper,
                    notificationBuilderWrapper = notificationBuilderWrapper
                ).show()

                val outputData: Data = workDataOf(KEY_IMAGE_URI to outputUri.toString())
                Result.success(outputData)
            } catch (throwable: Throwable) {
                Log.e(tag, applicationContext.resources.getString(R.string.error_applying_blur), throwable)
                Result.failure()
            }
        }
    }
}
