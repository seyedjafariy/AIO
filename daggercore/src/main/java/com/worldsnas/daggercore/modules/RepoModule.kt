package com.worldsnas.daggercore.modules

import com.worldsnas.domain.repo.home.HomeModule
import dagger.Module

@Module(
    includes = [
        HomeModule::class
    ]
)
abstract class RepoModule