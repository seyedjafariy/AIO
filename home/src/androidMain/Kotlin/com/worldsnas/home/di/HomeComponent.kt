package com.worldsnas.home.di

import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.lifecycle.LifecycleComponent
import com.worldsnas.daggercore.navigator.DefaultNavigationComponent
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.home.HomePresenter
import com.worldsnas.home.view.HomeView
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        CoreComponent::class,
        LifecycleComponent::class,
        DefaultNavigationComponent::class],
    modules = [
        HomeModule::class]
)
interface HomeComponent {
    fun inject(view: HomeView)

    @Component.Builder
    interface Builder {

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun lifecycleComponent(activityComponent: LifecycleComponent): Builder

        fun navComponent(navigationComponent: DefaultNavigationComponent): Builder
        fun build(): HomeComponent
    }
}
