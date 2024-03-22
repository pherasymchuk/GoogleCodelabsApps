package com.github.reply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.github.reply.ui.ReplyApp
import com.github.reply.ui.theme.ReplyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
//        Don't know how to hande insets here :(

        setContent {
            ReplyTheme {
                Surface(color = MaterialTheme.colorScheme.inverseOnSurface) {
                    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
                    val windowSize: WindowSizeClass = calculateWindowSizeClass(activity = this)
                    ReplyApp(
                        windowSize = windowSize.widthSizeClass,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
