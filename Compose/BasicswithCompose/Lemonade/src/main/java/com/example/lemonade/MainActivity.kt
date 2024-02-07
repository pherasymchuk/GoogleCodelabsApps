package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.BasicsWithComposeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsWithComposeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Lemonade", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xfff9e44c))
        )
    }) { innerPadding ->

        Surface(Modifier.padding(innerPadding)) {
            when (currentStep) {
                1 -> LemonadeTextAndImage(
                    text = "Tap the lemon tree to select a lemon",
                    image = painterResource(id = R.drawable.lemon_tree),
                    onImageClick = {
                        currentStep++
                        squeezeCount = Random.nextInt(1, 5)
                    }
                )

                2 -> LemonadeTextAndImage(
                    text = "Keep tapping the lemon to squeeze it",
                    image = painterResource(id = R.drawable.lemon_squeeze),
                    onImageClick = {
                        squeezeCount--
                        if (squeezeCount == 0) {
                            currentStep = 3
                        }
                    })

                3 -> LemonadeTextAndImage(
                    text = "Tap the lemonade to drink it",
                    image = painterResource(R.drawable.lemon_drink),
                    onImageClick = { currentStep = 4 })

                4 -> LemonadeTextAndImage(
                    text = "Tap the empty glass to start again",
                    image = painterResource(id = R.drawable.lemon_restart),
                    onImageClick = { currentStep = 1 })
            }
        }
    }
}

@Composable
fun LemonadeTextAndImage(text: String, image: Painter, onImageClick: () -> Unit, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onImageClick,
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFc3ecd2))
        ) {
            Image(painter = image, contentDescription = null, modifier = Modifier.padding(12.dp))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = text, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    BasicsWithComposeTheme {
        LemonadeApp()
    }
}

@Preview
@Composable
private fun LemonadeTextAndImagePreview() {
    LemonadeTextAndImage(
        text = "Tap the lemon tree to select a lemon",
        image = painterResource(id = R.drawable.lemon_tree),
        onImageClick = {}
    )
}
