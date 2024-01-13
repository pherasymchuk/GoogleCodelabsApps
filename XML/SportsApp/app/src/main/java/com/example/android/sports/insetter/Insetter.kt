package com.example.android.sports.insetter

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

interface Insetter {
    fun apply(
        top: Boolean = false,
        bottom: Boolean = false,
        left: Boolean = false,
        right: Boolean = false,
        consume: Boolean = false,
    )

    class Base(vararg view: View) : Insetter {
        private val views: List<View> = view.asList()

        override fun apply(
            top: Boolean,
            bottom: Boolean,
            left: Boolean,
            right: Boolean,
            consume: Boolean,
        ) {
            views.forEach {
                ViewCompat.setOnApplyWindowInsetsListener(it) { v, windowInsets ->
                    val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                    if (top) v.updatePadding(top = insets.top)
                    if (bottom) v.updatePadding(bottom = insets.bottom)
                    if (left) v.updatePadding(left = insets.left)
                    if (right) v.updatePadding(right = insets.right)
                    return@setOnApplyWindowInsetsListener if (consume) WindowInsetsCompat.CONSUMED else windowInsets
                }
            }
        }
    }
}
