package com.worldsnas.daggercore.modules

import com.worldsnas.domain.repo.discover.DiscoverModule
import com.worldsnas.domain.repo.featured.FeaturedModule
import dagger.Module

@Module(
    includes = [
        DiscoverModule::class,
        FeaturedModule::class
    ]
)
abstract class RepoModule