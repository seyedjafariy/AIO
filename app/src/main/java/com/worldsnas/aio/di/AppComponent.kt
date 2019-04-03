package com.worldsnas.aio.di

import com.worldsnas.core.di.CoreComponent
import com.worldsnas.core.di.modules.network.NetworkModule
import com.worldsnas.core.di.scope.AppScope
import dagger.Component
import javax.inject.Singleton

@Singleton
@AppScope
@Component(modules = [NetworkModule::class])
interface AppComponent : CoreComponent