package com.github.a30days

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.a30days.model.Day
import com.github.a30days.model.DaysRepository
import com.github.a30days.ui.theme.BasicsWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsWithComposeTheme {
                ThirtyDaysOfWellnessApp()
            }
        }
    }
}

@Composable
fun ThirtyDaysOfWellnessApp(modifier: Modifier = Modifier) {
    val days: List<Day> by remember { derivedStateOf { DaysRepository().getAllDays() } }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = modifier.padding(horizontal = 4.dp)
        ) {
            this.item {
                ThirtyDaysAppBar(modifier = Modifier.fillMaxWidth().wrapContentWidth())
            }
            this.items(days, key = { it.dayNumber }) { day ->
                OneDayCard(
                    dayNumber = day.dayNumber,
                    goal = stringResource(id = day.goalStringResId),
                    image = painterResource(id = day.imageResId),
                    description = stringResource(id = day.descriptionResId),
                    onClick = {},
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirtyDaysAppBar(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.app_bar_title),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )

}

@Composable
private fun OneDayCard(
    dayNumber: Int,
    goal: String,
    image: Painter,
    onClick: () -> Unit,
    description: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier, onClick = onClick) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DayNumber(
                dayNumber = dayNumber,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            DayGoal(goal)
            DayImage(
                image,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            DayDescription(description)
        }
    }
}

@Composable
private fun DayNumber(dayNumber: Int, modifier: Modifier = Modifier) {
    Text(
        text = "${stringResource(R.string.day)} $dayNumber",
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
    )
}

@Composable
private fun DayDescription(description: String, modifier: Modifier = Modifier) {
    Text(text = description, modifier = modifier)
}

@Composable
private fun DayImage(image: Painter, modifier: Modifier = Modifier) {
    Image(
        painter = image, contentDescription = null,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
    )
}

@Composable
private fun DayGoal(goal: String, modifier: Modifier = Modifier) {
    Text(
        text = goal,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
    )
}

@Preview
@Composable
private fun OneDayCardPreview() {
    BasicsWithComposeTheme {
        OneDayCard(
            dayNumber = 1,
            goal = "Read 6 pages",
            image = painterResource(id = R.drawable.daypicture1),
            description = "Should add later",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ThirtyDaysAppBarPreview() {
    BasicsWithComposeTheme {
        ThirtyDaysAppBar()
    }
}

@Preview
@Composable
private fun ThirtyDaysAppPreview() {
    ThirtyDaysOfWellnessApp()
}
