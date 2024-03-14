package com.github.lunchtray

import androidx.navigation.testing.TestNavHostController
import org.junit.Assert

fun TestNavHostController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(expectedRouteName, currentDestination?.route)
}
