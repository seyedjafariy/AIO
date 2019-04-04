package com.worldsnas.home.di

import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.home.HomeView
import dagger.Component

@FeatureScope
@Component(dependencies = [CoreComponent::class], modules = [HomeModule::class])
interface HomeComponent{
    fun inject(view : HomeView)
}
