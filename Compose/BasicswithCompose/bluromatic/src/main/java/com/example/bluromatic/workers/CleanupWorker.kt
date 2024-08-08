package com.example.bluromatic.workers

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bluromatic.CHANNEL_ID
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.OUTPUT_PATH
import com.example.bluromatic.R
import com.example.bluromatic.wrappers.NotificationBuilderWrapper
import com.example.bluromatic.wrappers.NotificationManagerWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

class CleanupWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val notificationBuilderWrapper = NotificationBuilderWrapper.Default(
        notificationBuilder = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
    )

    private val notificationManagerWrapper = NotificationManagerWrapper.Default(
        notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
    )

    override suspend fun doWork(): Result {
        StatusNotification.Default(
            message = "Cleaning up old temporary files",
            notificationManagerWrapper = notificationManagerWrapper,
            notificationBuilderWrapper = notificationBuilderWrapper
        ).show()

        return withContext(Dispatchers.IO) {
            delay(DELAY_TIME_MILLIS)

            try {
                val outPutDir = File(applicationContext.filesDir, OUTPUT_PATH)
                if (outPutDir.exists()) {
                    val entries: Array<out File>? = outPutDir.listFiles()
                    if (entries != null) {
                        for (entry in entries) {
                            val name: String = entry.name
                            if (name.isNotEmpty() && name.endsWith(".png")) {
                                val deleted: Boolean = entry.delete()
                                Log.i("Logs", "Deleted $name - $deleted")
                            }
                        }
                    }
                }
                Result.success()
            } catch (e: Exception) {
                Log.e(
                    "Logs",
                    applicationContext.resources.getString(R.string.error_cleaning_file),
                    e
                )
                Result.failure()
            }
        }

    }
}
