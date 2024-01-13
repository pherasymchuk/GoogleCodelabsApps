package com.example.android.sports

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class Behavior<V : View> : CoordinatorLayout.Behavior<V> {
    constructor() : super()
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){

    }

    override fun onApplyWindowInsets(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        windowInsets: WindowInsetsCompat
    ): WindowInsetsCompat {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        child.updatePadding(top = insets.top, bottom = insets.bottom)
        return super.onApplyWindowInsets(coordinatorLayout, child, windowInsets)
    }
}
