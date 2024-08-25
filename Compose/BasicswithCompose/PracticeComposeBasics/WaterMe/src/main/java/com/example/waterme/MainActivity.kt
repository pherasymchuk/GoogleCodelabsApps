package com.example.waterme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.waterme.ui.WaterMeApp
import com.example.waterme.ui.core.permission.NotificationPermission
import com.example.waterme.ui.theme.WaterMeTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val notificationPermission = NotificationPermission.Default(this)
//        notificationPermission.request()
        setContent {
            WaterMeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    WaterMeApp(
                        internalPadding = it,
                        notificationPermission = notificationPermission
                    )
                }
            }
        }
    }
}

