package com.worldsnas.aio.di

import com.worldsnas.daggercore.lifecycle.LifecycleEvent
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject

@Module
object LifecycleEventsModule {

    @JvmStatic
    @Provides
    fun provideEventSubject() =
        PublishSubject.create<LifecycleEvent>()


    @JvmStatic
    @Provides
    fun provideEventObservable(events : PublishSubject<LifecycleEvent>) =
        events.hide()
}
