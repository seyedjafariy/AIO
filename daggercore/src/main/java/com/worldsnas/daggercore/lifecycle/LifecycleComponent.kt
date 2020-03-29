package com.worldsnas.daggercore.lifecycle

import io.reactivex.Observable

interface LifecycleComponent {

    fun lifecycleEvents(): Observable<LifecycleEvent>
}