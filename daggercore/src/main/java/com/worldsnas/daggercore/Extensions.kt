package com.worldsnas.daggercore

import android.content.Context
import android.view.View
import com.bluelinelabs.conductor.Controller

fun View.coreComponent(): CoreComponent =
    if (context.applicationContext is CoreComponentProvider) {
        (context.applicationContext as CoreComponentProvider).core()
    } else {
        throw IllegalArgumentException("app class must implement CoreComponentProvider")
    }

fun Context.coreComponent(): CoreComponent =
    if (applicationContext is CoreComponentProvider) {
        (applicationContext as CoreComponentProvider).core()
    } else {
        throw IllegalArgumentException("app class must implement CoreComponentProvider")
    }

fun Context.activityComponent(): ActivityComponent =
    if (this is ActivityComponentProvider) {
        activityComponent()
    } else {
        throw IllegalArgumentException("Activity class must implement ActivityComponentProvider")
    }

fun Controller.activityComponent() : ActivityComponent =
        if(activity is ActivityComponentProvider){
            activityComponent()
        }else{
            throw IllegalArgumentException("Activity class must implement ActivityComponentProvider")
        }