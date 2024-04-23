package com.github.kotlinutils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KotlinExtensionsTest {
    @Test
    fun `PaddingValues addition isCorrect`() {
        val pv1 = PaddingValues(start = 5.dp, top = 6.dp, end = 7.dp, bottom = 8.dp)
        val pv2 = PaddingValues(start = 5.dp, top = 6.dp, end = 7.dp, bottom = 8.dp)
        val actual: PaddingValues = pv1 + pv2
        val expected = PaddingValues(start = 10.dp, top = 12.dp, end = 14.dp, bottom = 16.dp)
        val layoutDirection = LayoutDirection.Ltr
        assertEquals(actual.calculateStartPadding(layoutDirection), expected.calculateStartPadding(layoutDirection))
        assertEquals(actual.calculateTopPadding(), expected.calculateTopPadding())
        assertEquals(actual.calculateEndPadding(layoutDirection), expected.calculateEndPadding(layoutDirection))
        assertEquals(actual.calculateBottomPadding(), expected.calculateBottomPadding())
    }
}
