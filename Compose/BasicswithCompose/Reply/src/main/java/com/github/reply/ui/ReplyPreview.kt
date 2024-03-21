package com.github.reply.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Report
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.reply.R
import com.github.reply.data.MailboxType
import com.github.reply.ui.theme.ReplyTheme

@Composable
fun ReplyPreview(
    content: @Composable (
        viewModel: ReplyViewModel,
        uiState: ReplyUiState,
        navigationContent: List<NavigationItemContent>
    ) -> Unit = { _, _, _ -> },
) {
    val viewModel: ReplyViewModel = viewModel(ReplyViewModel.Base::class.java)
    val uiState: ReplyUiState = viewModel.uiState.collectAsState().value

    val navigationItemContentList = listOf(
        NavigationItemContent(
            mailboxType = MailboxType.Inbox,
            icon = Icons.Default.Inbox,
            text = stringResource(id = R.string.tab_inbox)
        ),
        NavigationItemContent(
            mailboxType = MailboxType.Sent,
            icon = Icons.AutoMirrored.Filled.Send,
            text = stringResource(id = R.string.tab_sent)
        ),
        NavigationItemContent(
            mailboxType = MailboxType.Drafts,
            icon = Icons.Default.Drafts,
            text = stringResource(id = R.string.tab_drafts)
        ),
        NavigationItemContent(
            mailboxType = MailboxType.Spam,
            icon = Icons.Default.Report,
            text = stringResource(id = R.string.tab_spam)
        )
    )

    ReplyTheme {
        content(viewModel, uiState, navigationItemContentList)
    }
}
