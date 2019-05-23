package com.worldsnas.moviedetail.di

import com.bluelinelabs.conductor.Router
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.navigator.NavigatorModule
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.moviedetail.view.MovieDetailView
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        CoreComponent::class],
    modules = [
        MovieDetailModule::class,
        NavigatorModule::class]
)
interface MovieDetailComponent {
    fun inject(view: MovieDetailView)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindRouter(router: Router): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): MovieDetailComponent
    }
}