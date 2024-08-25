package com.example.waterme

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.waterme.core.permission.NotificationPermission
import com.example.waterme.ui.WaterMeApp
import com.example.waterme.ui.theme.WaterMeTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterMeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    WaterMeApp(internalPadding = it)
                }
            }
        }
        val notificationPermission = NotificationPermission.Default(this)
        val isGranted = notificationPermission.request()
        Log.d(TAG, "onCreate: notificationPermission granted: $isGranted")
    }
}

