/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluromatic.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.bluromatic.ImageUri
import com.example.bluromatic.R
import com.example.bluromatic.data.blur.BlurAmount
import com.example.bluromatic.ui.core.PhotoPicker
import com.example.bluromatic.ui.theme.BluromaticTheme


@Composable
fun BluromaticScreen(
    blurViewModel: BlurViewModel = viewModel(
        factory = BlurViewModel.provideFactory(
            defaultImageUri = ImageUri.Drawable(
                LocalContext.current, R.drawable.android_cupcake
            ).uri().toString()
        ),
        modelClass = BlurViewModel.Default::class.java
    ),
) {
    val uiState: BlurViewModel.UiState by blurViewModel.uiState.collectAsStateWithLifecycle()
    val layoutDirection: LayoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            )
    ) {
        BluromaticScreenContent(
            loadingStatus = uiState.loadingStatus,
            imageUri = uiState.selectedImgUri,
            blurAmountOptions = uiState.blurAmount,
            applyBlur = blurViewModel::applyBlur,
            cancelWork = blurViewModel::cancelWork,
            onImageSelected = { uri ->
                blurViewModel.setImageUri(uri.toString())
            },
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
fun BluromaticScreenContent(
    modifier: Modifier = Modifier,
    loadingStatus: BlurViewModel.LoadingStatus,
    blurAmountOptions: List<BlurAmount>,
    applyBlur: (Int) -> Unit,
    cancelWork: () -> Unit,
    imageUri: String = Uri.EMPTY.toString(),
    onImageSelected: (Uri) -> Unit = {},
) {
    var selectedValue by rememberSaveable { mutableStateOf(1) }
    val context = LocalContext.current
    Column(
        modifier = modifier.scrollable(
            state = rememberScrollState(),
            orientation = Orientation.Vertical
        )
    ) {
        PhotoPicker(onImageSelected = onImageSelected) {
            AsyncImage(
                model = imageUri,
                contentDescription = stringResource(R.string.description_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Fit,
            )
        }
        BlurAmountContent(
            selectedValue = selectedValue,
            blurAmounts = blurAmountOptions,
            modifier = Modifier.fillMaxWidth(),
            onSelectedValueChange = { selectedValue = it }
        )
        BlurActions(
            loadingStatus = loadingStatus,
            onStartClick = { applyBlur(selectedValue) },
            onSeeFileClick = { BlurredImage(context, it).show() },
            onCancelClick = { cancelWork() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BlurActions(
    modifier: Modifier = Modifier,
    loadingStatus: BlurViewModel.LoadingStatus,
    onStartClick: () -> Unit,
    onSeeFileClick: (String) -> Unit,
    onCancelClick: () -> Unit,
) {

    AnimatedContent(
        targetState = loadingStatus,
        label = "Different button animation",
        modifier = Modifier
    ) { currentStatus: BlurViewModel.LoadingStatus ->
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            when (currentStatus) {
                is BlurViewModel.LoadingStatus.Idle -> {
                    Button(
                        onClick = onStartClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.start))
                    }
                }

                is BlurViewModel.LoadingStatus.Loading -> {
                    FilledTonalButton(
                        onCancelClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.cancel_work))
                    }
                    CircularProgressIndicator(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
                }

                is BlurViewModel.LoadingStatus.Complete -> {
                    Button(
                        onClick = onStartClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.start))
                    }
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                    FilledTonalButton(
                        onClick = { onSeeFileClick(currentStatus.outputUri) }
                    ) {
                        Text(stringResource(R.string.see_file))
                    }
                }

            }
        }
    }
}


@Composable
fun BlurAmountContent(
    selectedValue: Int,
    blurAmounts: List<BlurAmount>,
    modifier: Modifier = Modifier,
    onSelectedValueChange: (Int) -> Unit,
) {
    Column(
        modifier = modifier.selectableGroup()
    ) {
        Text(
            text = stringResource(R.string.blur_title),
            style = MaterialTheme.typography.headlineSmall
        )
        blurAmounts.forEach { amount ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        role = Role.RadioButton,
                        selected = (selectedValue == amount.blurAmount),
                        onClick = { onSelectedValueChange(amount.blurAmount) }
                    )
                    .size(48.dp)
            ) {
                RadioButton(
                    selected = (selectedValue == amount.blurAmount),
                    onClick = null,
                    modifier = Modifier.size(48.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(stringResource(amount.blurAmountRes))

            }
        }
    }
}

class BlurredImage(
    private val context: Context,
    private val imageUri: String,
) {
    fun show() {
        val uri = if (imageUri.isNotEmpty()) {
            Uri.parse(imageUri)
        } else {
            null
        }
        try {
            val actionView = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(actionView)
        } catch (e: ActivityNotFoundException) {
            Log.e("Logs", "show: No activity found to handle")
        }
    }
}

@Preview(showBackground = true)
@PreviewParameter(BluromaticPreviewParameterProvider::class)
@Composable
fun BluromaticScreenContentPreview(
    @PreviewParameter(BluromaticPreviewParameterProvider::class) loadingStatus: BlurViewModel.LoadingStatus,
) {
    BluromaticTheme {
        BluromaticScreenContent(
            loadingStatus = loadingStatus,
            blurAmountOptions = listOf(BlurAmount(R.string.blur_lv_1, 1)),
            applyBlur = {},
            cancelWork = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

class BluromaticPreviewParameterProvider : PreviewParameterProvider<BlurViewModel.LoadingStatus> {
    override val values = sequenceOf(
        BlurViewModel.LoadingStatus.Idle,
        BlurViewModel.LoadingStatus.Loading,
        BlurViewModel.LoadingStatus.Complete("file:///path/to/file")
    )
}
