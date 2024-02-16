package com.github.superheroes

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.superheroes.model.HeroesRepository
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HeroTopAppBar(modifier = Modifier.alpha(0.9f)) }
    ) { innerPadding ->
        val heroes by remember { derivedStateOf { HeroesRepository.heroes } }

        LazyColumn(contentPadding = innerPadding, modifier = Modifier.padding(horizontal = 8.dp)) {
            items(heroes) { hero ->
                HeroItem(
                    name = stringResource(id = hero.nameRes),
                    description = stringResource(id = hero.descriptionRes),
                    image = painterResource(id = hero.imageRes),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge
        )
    }, modifier = modifier)
}

@Composable
fun HeroItem(
    name: String,
    description: String,
    image: Painter,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            HeroInfo(
                name = name,
                description = description,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(16.dp))
            HeroImage(
                painter = image, modifier = Modifier
                    .size(72.dp)
            )
        }
    }
}

@Composable
fun HeroImage(painter: Painter, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(72.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Image(
            painter = painter,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            alignment = Alignment.TopCenter
        )
    }
}

@Composable
fun HeroInfo(name: String, description: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = name, style = MaterialTheme.typography.displaySmall)
        Text(text = description, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun HeroItemPreview() {
    BasicsWithComposeTheme {
        HeroItem(
            name = stringResource(id = R.string.hero1),
            description = stringResource(id = R.string.description1),
            image = painterResource(id = R.drawable.android_superhero1)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsWithComposeTheme {
        SuperheroesApp()
    }
}
