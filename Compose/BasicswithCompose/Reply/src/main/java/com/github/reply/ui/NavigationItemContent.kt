package com.github.reply.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.github.reply.data.MailboxType

data class NavigationItemContent(
    val mailboxType: MailboxType,
    val icon: ImageVector,
    val text: String,
)
