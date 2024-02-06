package com.example.art

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.art.ui.theme.BasicsWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsWithComposeTheme {
                Scaffold {
                    ArtScreen(
                        modifier = Modifier
                            .padding(it)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ArtScreen(modifier: Modifier = Modifier) {
    val pictures: List<BiblePicture> by remember { derivedStateOf { BiblePictureRepository().getPictures() } }
    var currentScreen: Int by remember { mutableIntStateOf(1) }
    var currentPicture: BiblePicture by remember { mutableStateOf(pictures.first()) }

    Column(modifier = modifier) {
        ArtworkWall(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .wrapContentSize(Alignment.Center),
            imageRes = currentPicture.imageRes
        )
        ArtworkDescriptor(currentPicture.verseText, bibleChapter = currentPicture.verseNumber)
        DisplayController()
    }
}

@Composable
fun ArtworkWall(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier
) {
    val image = painterResource(id = imageRes)
    Card(
        modifier = modifier.shadow(elevation = 8.dp),
        shape = AbsoluteCutCornerShape(0),
        colors = CardDefaults.elevatedCardColors(),
    ) {
        Image(painter = image, contentDescription = null, Modifier.padding(24.dp))
    }
}

@Composable
fun ArtworkDescriptor(
    @StringRes name: Int,
    @StringRes bibleChapter: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = stringResource(R.string.bible_story_text_1),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
            Text(text = stringResource(R.string.bible_story_verse_1), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DisplayController(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { /*TODO*/ }, modifier = Modifier.width(150.dp)) {
            Text(text = "Previous")
        }

        Button(onClick = { /*TODO*/ }, modifier = Modifier.width(150.dp)) {

            Text(text = "Next")
        }
    }
}

@Preview
@Composable
private fun ArtworkWallPreview() {
    ArtworkWall(imageRes = R.drawable.bible_story1)
}

@Preview
@Composable
private fun ArtworkDescriptorPreview() {
    ArtworkDescriptor(name = R.string.bible_story_text_1, bibleChapter = R.string.bible_story_verse_1)
}

@Preview
@Composable
private fun DisplayControllerPreview() {
    DisplayController()
}

@Preview(showBackground = true)
@Composable
fun ArtScreenPreview() {
    BasicsWithComposeTheme {
        ArtScreen()
    }
}
