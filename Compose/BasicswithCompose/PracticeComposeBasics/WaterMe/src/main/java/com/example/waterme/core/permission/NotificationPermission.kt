package com.example.waterme.core.permission

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@TargetApi(33)
interface NotificationPermission : Permission {

    class Default(private val activity: ComponentActivity) : NotificationPermission {

        override fun request(): Boolean {
            return when {
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    true
                }

                else -> {
                    // Directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    var isGranted = false
                    activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                        isGranted = it
                    }.launch(Manifest.permission.POST_NOTIFICATIONS)
                    return isGranted
                }
            }
        }

        override fun shouldShowRationale(): Boolean {
            return ActivityCompat.shouldShowRequestPermissionRationale(
                activity, Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }
}
