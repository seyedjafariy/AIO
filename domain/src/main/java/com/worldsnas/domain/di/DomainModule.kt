package com.worldsnas.domain.di

import com.worldsnas.domain.repo.home.HomeRepoModule
import dagger.Module

@Module(
    includes = [
        MappersModule::class,
        HomeRepoModule::class]
)
abstract class DomainModule