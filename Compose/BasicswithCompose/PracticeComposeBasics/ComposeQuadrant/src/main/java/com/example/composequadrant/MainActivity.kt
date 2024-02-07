package com.example.composequadrant

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composequadrant.ui.theme.BasicsWithComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsWithComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ComposeQuadrantScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

}

@Composable
fun ComposeQuadrantScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f)) {
            Quadrant(
                heading = "Text composable",
                text = "Displays text and follows the recommended Material Design guidelines.",
                background = Color(0xFFEADDFF),
                Modifier.weight(1f)
            )
            Quadrant(
                heading = "Image composable",
                text = "Creates a composable that lays out and draws a given Painter class object.",
                background = Color(0xFFD0BCFF),
                Modifier.weight(1f)
            )
        }
        Row(Modifier.weight(1f)) {
            Quadrant(
                heading = "Row composable",
                text = "A layout composable that places its children in a horizontal sequence.",
                background = Color(0xFFB69DF8),
                Modifier.weight(1f)
            )
            Quadrant(
                heading = "Column composable",
                text = "A layout composable that places its children in a vertical sequence.",
                background = Color(0xFFF6EDFF),
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Quadrant(
    heading: String,
    text: String,
    background: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = heading,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = text)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    BasicsWithComposeTheme {
        ComposeQuadrantScreen()
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 500)
@Composable
private fun QuadrantPreview() {
    Quadrant(
        heading = "Test heading",
        text = "Displays text and follows the recommended Material Design guidelines.",
        background = Color.Yellow
    )
}
