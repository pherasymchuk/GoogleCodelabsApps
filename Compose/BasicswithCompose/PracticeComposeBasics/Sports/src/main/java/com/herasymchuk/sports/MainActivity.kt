package com.herasymchuk.sports

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.sports.ui.SportsApp
import com.herasymchuk.sports.ui.theme.SportsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportsTheme {
                Surface {
                    SportsApp()
                }
            }
        }
    }
}
