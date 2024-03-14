package com.github.lunchtray.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.lunchtray.R
import com.github.lunchtray.onNodeWithStringId
import com.github.lunchtray.ui.StartOrderScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StartOderScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun init() {
        composeRule.setContent { StartOrderScreen(onStartOrderButtonClicked = { }) }
    }

    @Test
    fun startOrderScreen_BackButtonIsNotShown() {
        composeRule.onNodeWithStringId(R.string.back_button, true).assertDoesNotExist()
    }
}
