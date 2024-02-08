package com.example.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations.model.Affirmation
import com.example.affirmations.model.Datasource
import com.example.affirmations.ui.theme.BasicsWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsWithComposeTheme {
                AffirmationsApp(
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun AffirmationsApp(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()
        ) {
            items(items = Datasource().loadAffirmations()) { affirmation ->
                AffirmationCard(affirmation = affirmation, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(id = affirmation.imageResId),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = affirmation.stringResId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp)
            )
            Text(
                text = stringResource(id = affirmation.stringResId),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AffirmationsPreview() {
    BasicsWithComposeTheme {
        AffirmationsApp()
    }
}

@Preview
@Composable
private fun AffirmationPreview() {
    AffirmationCard(Affirmation(R.string.affirmation1, R.drawable.image1))
}
