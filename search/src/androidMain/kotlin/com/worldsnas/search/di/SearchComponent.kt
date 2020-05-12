package com.worldsnas.search.di

import com.bluelinelabs.conductor.Router
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.navigator.NavigatorModule
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.search.view.SearchView
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        CoreComponent::class],
    modules = [
        SearchModule::class,
        NavigatorModule::class]
)
interface SearchComponent {
    fun inject(view: SearchView)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindRouter(router: Router): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): SearchComponent
    }
}