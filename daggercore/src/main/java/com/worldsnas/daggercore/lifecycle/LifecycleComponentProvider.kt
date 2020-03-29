package com.worldsnas.daggercore.lifecycle

import com.worldsnas.daggercore.lifecycle.LifecycleComponent

interface LifecycleComponentProvider {

    fun lifecycleComponent() : LifecycleComponent
}