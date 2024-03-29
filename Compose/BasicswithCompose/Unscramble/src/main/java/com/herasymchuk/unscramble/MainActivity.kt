package com.herasymchuk.unscramble

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.herasymchuk.unscramble.ui.theme.BasicsWithComposeTheme
import com.herasymchuk.unscramble.ui.theme.GameScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            BasicsWithComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    GameScreen(innerPadding = it, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
