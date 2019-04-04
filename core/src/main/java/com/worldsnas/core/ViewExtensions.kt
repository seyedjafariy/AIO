package com.worldsnas.core

import android.view.View

@Suppress("LiftReturnOrAssignment")
infix fun View.visible(visible: Boolean) =
    if (visible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }