package com.worldsnas.daggercore.modules

import com.worldsnas.domain.repo.home.HomeRepoModule
import dagger.Module

@Module(
    includes = [
        HomeRepoModule::class
    ]
)
abstract class RepoModule