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

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.reply.data.Email
import com.github.reply.data.MailboxType
import com.github.reply.data.local.LocalEmailsDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ReplyViewModel : ViewModel() {
    abstract val uiState: StateFlow<ReplyUiState>
    abstract val homeScreenScrollState: MutableState<LazyListState>

    protected abstract fun initializeUIState()
    abstract fun updateCurrentEmail(email: Email)
    abstract fun selectDetailsScreen()
    abstract fun resetHomeScreenStates()
    abstract fun updateCurrentMailbox(mailboxType: MailboxType)

    class Base : ReplyViewModel() {
        override val uiState: MutableStateFlow<ReplyUiState> = MutableStateFlow(ReplyUiState())
        override var homeScreenScrollState: MutableState<LazyListState> = mutableStateOf(LazyListState())

        init {
            homeScreenScrollState
            initializeUIState()
            // Log changing screens
            viewModelScope.launch {
                uiState.collect { uiState ->
                    Log.i(TAG, "Current mailbox: ${uiState.currentMailbox}")
                }
            }
        }

        override fun initializeUIState() {
            val mailboxes: Map<MailboxType, List<Email>> =
                LocalEmailsDataProvider.allEmails.groupBy { it.mailbox }
            Log.i(TAG, "initializeUIState: mailboxes -> ${mailboxes.map { "\n\t$it" }}")
            uiState.value = ReplyUiState(
                mailboxes = mailboxes,
                currentSelectedEmail = mailboxes[MailboxType.Inbox]?.get(0) ?: LocalEmailsDataProvider.defaultEmail
            )
        }

        override fun updateCurrentEmail(email: Email) {
            uiState.update { it: ReplyUiState ->
                it.copy(
                    currentSelectedEmail = email,
                )
            }
        }

        override fun selectDetailsScreen() {
            uiState.update { it: ReplyUiState ->
                it.copy(isShowingHomepage = false)
            }
        }

        override fun resetHomeScreenStates() {
            uiState.update { it: ReplyUiState ->
                it.copy(
                    currentSelectedEmail = it.mailboxes[it.currentMailbox]?.get(0)
                        ?: LocalEmailsDataProvider.defaultEmail,
                    isShowingHomepage = true
                )
            }
        }

        override fun updateCurrentMailbox(mailboxType: MailboxType) {
            uiState.update { it: ReplyUiState ->
                it.copy(
                    currentMailbox = mailboxType
                )
            }
        }
    }

    companion object {
        private const val TAG = "ReplyLogs"
    }
}
