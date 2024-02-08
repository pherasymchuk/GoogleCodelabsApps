package com.example.art

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.art.ui.theme.BasicsWithComposeTheme
import org.junit.Rule
import org.junit.Test

class ArtUiTests {
    @get: Rule
    val rule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun screen_should_change_correctly() {
        rule.setContent {
            BasicsWithComposeTheme {
                Art()
            }
        }
        rule.onNodeWithText("Next").performClick().performClick().performClick()
        correct_text_and_verse_is_displayed()
        correct_picture_is_displayed()
    }

    private fun correct_text_and_verse_is_displayed() {
        rule.onNodeWithText(context.getString(R.string.bible_story_text_4)).assertExists(
            "Screen 4 has wrong description"
        )
        rule.onNodeWithText(context.getString(R.string.bible_story_verse_4)).assertExists(
            "Screen 4 has wrong verse number"
        )
    }

    private fun correct_picture_is_displayed() {
        rule.onNodeWithTag(R.drawable.bible_story4.toString()).assertExists(
            "Screen 4 has wrong image"
        )
    }
}
