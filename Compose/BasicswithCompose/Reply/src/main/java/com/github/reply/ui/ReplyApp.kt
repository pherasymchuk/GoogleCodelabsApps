/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.reply.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.reply.data.MailboxType
import com.github.reply.ui.theme.ReplyTheme

@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: ReplyViewModel = viewModel<ReplyViewModel.Base>()
    val replyUiState: ReplyUiState by viewModel.uiState.collectAsState()

    if (replyUiState.isShowingHomepage) {
        ReplyHomeScreen(
            windowSize = windowSize,
            replyUiState = replyUiState,
            onTabPressed = { mailboxType: MailboxType ->
                viewModel.updateCurrentMailbox(mailboxType)
                viewModel.resetHomeScreenStates()
            },
            viewModel = viewModel,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        ReplyDetailsScreen(
            showBackButton = true,
            showTopBar = true,
            replyUiState = replyUiState,
            onBackPressed = viewModel::resetHomeScreenStates,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReplyAppCompactPreview() {
    ReplyTheme {
        Surface {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
private fun ReplyAppMediumPreview() {
    ReplyTheme {
        Surface {
            ReplyApp(windowSize = WindowWidthSizeClass.Medium)
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ReplyAppExpandedPreview() {
    ReplyTheme {
        Surface {
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
        }
    }
}
