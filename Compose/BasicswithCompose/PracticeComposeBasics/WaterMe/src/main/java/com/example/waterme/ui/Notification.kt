package com.example.waterme.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.waterme.R

interface Notification {
    /**
     * Displays this notification.
     *
     * @return `true` if the notification was successfully shown, `false` otherwise.
     */
    fun show(): Boolean

    class WaterReminder(
        private val message: String,
        private val context: Context,
    ) : Notification {
        private val channelId = "VERBOSE_NOTIFICATION"
        private val verboseNotificationChannelName: CharSequence = "Verbose WorkManager Notifications"
        private val verboseNotificationChannelDescription = "Shows notifications whenever work starts"
        private val importance = NotificationManager.IMPORTANCE_DEFAULT
        private val notificationId = 1
        private val channel = NotificationChannel(channelId, verboseNotificationChannelName, importance)

        override fun show(): Boolean {
            channel.description = verboseNotificationChannelDescription
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)

            val notification: android.app.Notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Water me!")
                .setContentText(message)
                .setVibrate(LongArray(0))
                .setAutoCancel(true)
                .build()

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            NotificationManagerCompat.from(context).notify(notificationId, notification)
            return true
        }
    }
}
