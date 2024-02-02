package com.example.composearticle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composearticle.ui.theme.BasicsWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsWithComposeTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Article()
                    }
                }
            }
        }
    }
}

@Composable
fun Article(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.bg_compose_background)
    Column(modifier = modifier.fillMaxSize()) {
        Image(painter = image, contentDescription = null)
        ArticleHeading(text = stringResource(id = R.string.jetpack_compose_tutorial))
        ArticleContent(
            text = stringResource(id = R.string.what_is_jetpack_compose_text),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        ArticleContent(
            text = stringResource(id = R.string.tutorial_description),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ArticleHeading(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun ArticleContent(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, textAlign = TextAlign.Justify)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsWithComposeTheme {
        Article()
    }
}
