package com.herasymchuk.dessertclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.herasymchuk.dessertclicker.ui.DessertClickerApp
import com.herasymchuk.dessertclicker.ui.theme.BasicsWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            BasicsWithComposeTheme {
                DessertClickerApp()
            }
        }
    }
}
