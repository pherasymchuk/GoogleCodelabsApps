package com.github.a30days.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Day(
    val dayNumber: Int,
    @StringRes val goalStringResId: Int,
    @DrawableRes val imageResId: Int,
    @StringRes val descriptionResId: Int
)
