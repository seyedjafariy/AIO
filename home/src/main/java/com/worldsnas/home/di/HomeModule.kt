package com.worldsnas.home.di

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomePresenter
import com.worldsnas.home.HomeState
import com.worldsnas.mvi.MviPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {

    @Binds
    @FeatureScope
    abstract fun bindPresenter(presenter: HomePresenter):
        MviPresenter<HomeIntent, HomeState>
}