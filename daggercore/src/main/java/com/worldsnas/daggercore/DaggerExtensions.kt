package com.worldsnas.daggercore

import android.app.Activity
import android.content.Context
import android.view.View
import com.worldsnas.daggercore.lifecycle.LifecycleComponent
import com.worldsnas.daggercore.lifecycle.LifecycleComponentProvider

fun View.coreComponent(): CoreComponent =
    context.coreComponent()

fun Context.coreComponent(): CoreComponent =
    if (applicationContext is CoreComponentProvider) {
        (applicationContext as CoreComponentProvider).core()
    } else {
        throw IllegalArgumentException("app class must implement CoreComponentProvider")
    }

val Activity.lifecycleComponent: LifecycleComponent
    get() =
        if (this is LifecycleComponentProvider) {
            this.lifecycleComponent()
        } else {
            throw IllegalStateException("activity class must implement LifecycleComponentProvider $this")
        }