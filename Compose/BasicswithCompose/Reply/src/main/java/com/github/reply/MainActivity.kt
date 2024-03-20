package com.github.reply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.reply.ui.ReplyApp
import com.github.reply.ui.theme.ReplyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Don't know how to hande insets here :(
//        enableEdgeToEdge()

        setContent {
            ReplyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
                    val windowSize: WindowSizeClass = calculateWindowSizeClass(activity = this)
                    ReplyApp(
                        windowSize = windowSize.widthSizeClass,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyAppCompactPreview() {
    ReplyTheme {
        Surface {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }
}
