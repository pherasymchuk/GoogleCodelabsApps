package com.github.superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.superheroes.ui.theme.BasicsWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsWithComposeTheme {
                SuperheroesApp()
            }
        }
    }
}

@Composable
fun SuperheroesApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsWithComposeTheme {
        SuperheroesApp()
    }
}
