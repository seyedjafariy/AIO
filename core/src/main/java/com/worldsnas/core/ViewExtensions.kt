package com.worldsnas.core

import android.view.View
import com.worldsnas.core.di.CoreComponent
import com.worldsnas.core.di.CoreComponentProvider

@Suppress("LiftReturnOrAssignment")
infix fun View.visible(visible: Boolean) =
    if (visible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }

fun View.coreComponent(): CoreComponent =
    if (context.applicationContext is CoreComponentProvider) {
        (context.applicationContext as CoreComponentProvider).core()
    } else {
        throw IllegalArgumentException("app class must implement CoreComponentProvider")
    }