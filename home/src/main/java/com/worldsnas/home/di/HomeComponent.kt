package com.worldsnas.home.di

import com.bluelinelabs.conductor.Router
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.navigator.NavigatorModule
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.home.HomePresenter
import com.worldsnas.home.view.HomeView
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        CoreComponent::class],
    modules = [
        HomeModule::class,
        NavigatorModule::class]
)
interface HomeComponent {
    fun inject(view: HomeView)

    fun presenter() : HomePresenter

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindRouter(router: Router): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): HomeComponent
    }
}
