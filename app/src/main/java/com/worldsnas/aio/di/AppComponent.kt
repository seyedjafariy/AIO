package com.worldsnas.aio.di

import com.worldsnas.core.di.CoreComponent
import com.worldsnas.core.di.scope.AppScope
import dagger.Component

@AppScope
@Component()
interface AppComponent : CoreComponent {
}