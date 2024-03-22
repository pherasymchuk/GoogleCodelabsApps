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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.reply.R
import com.github.reply.data.Email
import com.github.reply.data.MailboxType
import com.github.reply.data.local.LocalAccountsDataProvider

@Composable
fun ReplyHomeScreen(
    windowSize: WindowWidthSizeClass,
    viewModel: ReplyViewModel,
    replyUiState: ReplyUiState,
    onTabPressed: (MailboxType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navigationContentList: List<NavigationItemContent> =
        replyUiState.navigationItemContentList(LocalContext.current)

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            ReplyCompactHomeScreen(
                replyUiState = replyUiState,
                onTabPressed = onTabPressed,
                onEmailCardPressed = { email: Email ->
                    viewModel.updateCurrentEmail(email)
                    viewModel.selectDetailsScreen()
                },
                navigationItemContentList = navigationContentList,
                modifier = modifier.fillMaxSize()
            )
        }

        WindowWidthSizeClass.Medium -> {
            ReplyMediumHomeScreen(
                replyUiState = replyUiState,
                onTabPressed = onTabPressed,
                onEmailCardPressed = { email: Email ->
                    viewModel.updateCurrentEmail(email)
                    viewModel.selectDetailsScreen()
                },
                navigationItemContentList = navigationContentList,
                modifier = modifier
            )
        }

        WindowWidthSizeClass.Expanded -> {
            ReplyLargeHomeScreen(
                replyUiState = replyUiState,
                onTabPressed = onTabPressed,
                onEmailCardPressed = { email: Email ->
                    viewModel.updateCurrentEmail(email)
                },
                navigationItemContentList = navigationContentList,
                modifier = modifier.fillMaxSize()
            )
        }
    }

}

@Composable
fun ReplyCompactHomeScreen(
    replyUiState: ReplyUiState,
    onTabPressed: (MailboxType) -> Unit,
    onEmailCardPressed: (Email) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            ReplyBottomNavigationBar(
                currentTab = replyUiState.currentMailbox,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }, modifier = Modifier.fillMaxSize()
    ) { it: PaddingValues ->
        Row(modifier = Modifier) {
            ReplyListOnlyContent(
                replyUiState = replyUiState,
                onEmailCardPressed = onEmailCardPressed,
                innerPadding = it,
                modifier = modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.email_list_only_horizontal_padding))
                    .consumeWindowInsets(it)
            )
        }
    }
}


@Composable
fun ReplyMediumHomeScreen(
    replyUiState: ReplyUiState,
    onTabPressed: (MailboxType) -> Unit,
    onEmailCardPressed: (Email) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        val navigationRailContentDescription: String = stringResource(R.string.navigation_rail)
        ReplyNavigationRail(
            currentTab = replyUiState.currentMailbox,
            onTabPressed = onTabPressed,
            navigationItemContentList = navigationItemContentList,
            modifier = Modifier
                .testTag(navigationRailContentDescription)
                .wrapContentWidth()
        )
        ReplyListOnlyContent(
            replyUiState = replyUiState,
            onEmailCardPressed = onEmailCardPressed,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.email_list_only_horizontal_padding))
        )
    }
}

@Composable
fun ReplyLargeHomeScreen(
    replyUiState: ReplyUiState,
    onTabPressed: (MailboxType) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    onEmailCardPressed: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    Modifier.width(dimensionResource(id = R.dimen.drawer_width)),
                    drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                ) {
                    NavigationDrawerContent(
                        selectedDestination = replyUiState.currentMailbox,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(start = dimensionResource(id = R.dimen.drawer_padding_content))
                    )
                }
            }
        ) {
            ReplyListAndDetailContent(
                replyUiState = replyUiState,
                onEmailCardPressed = onEmailCardPressed,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ReplyNavigationRail(
    currentTab: MailboxType,
    onTabPressed: ((MailboxType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier) {
        for (navItem: NavigationItemContent in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.mailboxType,
                onClick = { onTabPressed(navItem.mailboxType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}

@Composable
private fun ReplyBottomNavigationBar(
    currentTab: MailboxType,
    onTabPressed: ((MailboxType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        for (navItem: NavigationItemContent in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.mailboxType,
                onClick = { onTabPressed(navItem.mailboxType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                label = { Text(text = navItem.text) }
            )
        }
    }
}

@Composable
private fun NavigationDrawerContent(
    selectedDestination: MailboxType,
    onTabPressed: ((MailboxType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        NavigationDrawerHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.profile_image_padding)),
        )
        for (navItem: NavigationItemContent in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.mailboxType,
                label = {
                    Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem.mailboxType) }
            )
        }
    }
}

@Composable
private fun NavigationDrawerHeader(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ReplyLogo(modifier = Modifier.size(dimensionResource(R.dimen.reply_logo_size)))
        ReplyProfileImage(
            drawableResource = LocalAccountsDataProvider.defaultAccount.avatar,
            description = stringResource(id = R.string.profile),
            modifier = Modifier
                .size(dimensionResource(R.dimen.profile_image_size))
        )
    }
}

@Preview
@Composable
private fun ReplyNavigationContentPreview() {
    ReplyPreview { _, uiState, navigationContent ->
        NavigationDrawerContent(
            selectedDestination = uiState.currentMailbox,
            onTabPressed = {},
            navigationItemContentList = navigationContent,
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(start = dimensionResource(id = R.dimen.drawer_padding_content))
        )
    }
}

@Preview
@Composable
private fun NavigationDrawerHeaderPreview() {
    ReplyPreview { _, _, _ ->
        NavigationDrawerHeader()
    }
}

@Preview
@Composable
private fun ReplyNavigationBarPreview() {
    ReplyPreview { _, uiState, navigationContent ->
        ReplyBottomNavigationBar(
            currentTab = uiState.currentMailbox,
            onTabPressed = {},
            navigationItemContentList = navigationContent,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun ReplyNavigationRailPreview() {
    ReplyPreview { _, replyUiState, navigationItemContents ->
        ReplyNavigationRail(
            currentTab = replyUiState.currentMailbox,
            onTabPressed = {},
            navigationItemContentList = navigationItemContents
        )
    }
}
