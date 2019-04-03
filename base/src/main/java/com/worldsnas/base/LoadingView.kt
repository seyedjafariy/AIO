package com.worldsnas.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        View.inflate(context, R.layout.view_loading, this)
    }
}