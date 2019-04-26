package com.worldsnas.daggercore

import android.content.Context
import android.view.View

fun View.coreComponent(): CoreComponent =
    if (context.applicationContext is CoreComponentProvider) {
        (context.applicationContext as CoreComponentProvider).core()
    } else {
        throw IllegalArgumentException("app class must implement CoreComponentProvider")
    }

fun Context.coreComponent() : CoreComponent =
    if (applicationContext is CoreComponentProvider) {
        (applicationContext as CoreComponentProvider).core()
    } else {
        throw IllegalArgumentException("app class must implement CoreComponentProvider")
    }