package com.worldsnas.aio.di

import com.worldsnas.aio.MainActivity
import com.worldsnas.daggercore.lifecycle.LifecycleComponent
import com.worldsnas.daggercore.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(
    modules = [LifecycleEventsModule::class]
)
interface ActivityComponent :
    LifecycleComponent {

    fun inject(activity: MainActivity)
}