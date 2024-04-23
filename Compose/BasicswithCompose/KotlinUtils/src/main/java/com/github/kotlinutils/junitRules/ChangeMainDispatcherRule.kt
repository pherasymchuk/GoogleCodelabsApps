@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.kotlinutils.junitRules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestWatcher

class ChangeMainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : BeforeTestExecutionCallback, AfterTestExecutionCallback, TestWatcher {
    override fun beforeTestExecution(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterTestExecution(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}
