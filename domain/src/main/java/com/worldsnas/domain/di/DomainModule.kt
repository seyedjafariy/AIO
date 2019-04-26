package com.worldsnas.domain.di

import com.worldsnas.domain.helpers.ImageInfo
import com.worldsnas.domain.helpers.TMDBImageUrlFactory
import com.worldsnas.domain.repo.home.HomeRepoModule
import com.worldsnas.panther.Factory
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        MappersModule::class,
        HomeRepoModule::class]
)
abstract class DomainModule{

    @Binds
    abstract fun bindUrlFactory(factory : TMDBImageUrlFactory) :
        Factory<ImageInfo, String>
}