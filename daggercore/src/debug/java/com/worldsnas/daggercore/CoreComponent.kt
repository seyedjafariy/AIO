package com.worldsnas.daggercore

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.worldsnas.daggercore.modules.CoreModule
import com.worldsnas.daggercore.modules.DatabaseModule
import com.worldsnas.daggercore.modules.network.NetworkModule
import com.worldsnas.daggercore.scope.AppScope
import com.worldsnas.domain.di.DomainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@AppScope
@Component(
    modules = [
        NetworkModule::class,
        DomainModule::class,
        DatabaseModule::class,
        CoreModule::class,
        FrescoModule::class]
)
interface CoreComponent : BaseComponent{

    fun networkFlipperPlugin(): NetworkFlipperPlugin

    @Component.Builder
    interface Builder : BaseComponent.Builder {
        override fun build(): CoreComponent
    }
}