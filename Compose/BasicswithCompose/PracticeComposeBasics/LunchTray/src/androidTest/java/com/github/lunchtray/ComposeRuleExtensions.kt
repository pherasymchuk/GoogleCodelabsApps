package com.github.lunchtray

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.lunchtray.datasource.DataSource

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int,
    ignoreCase: Boolean = false
): SemanticsNodeInteraction {
    return onNodeWithText(activity.getString(id), ignoreCase = ignoreCase)
}

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.chooseRandomEntry() {
    onNodeWithText(DataSource.entreeMenuItems.random().name).performClick()
}
