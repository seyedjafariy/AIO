package com.worldsnas.daggercore.modules

import com.worldsnas.domain.repo.explore.ExploreModule
import com.worldsnas.domain.repo.featured.FeaturedModule
import dagger.Module

@Module(
    includes = [
        ExploreModule::class,
        FeaturedModule::class
    ]
)
abstract class RepoModule