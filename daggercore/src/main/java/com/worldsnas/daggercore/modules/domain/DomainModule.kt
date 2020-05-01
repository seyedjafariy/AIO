package com.worldsnas.daggercore.modules.domain

import dagger.Module

@Module(
    includes = [
        MappersModule::class,
        HomeRepoModule::class,
        MovieDetailRepoModule::class,
        SearchRepoModule::class,
        GenreRepoModule::class,
        PeopleRepoModule::class,
        DBModule::class
    ]
)
abstract class DomainModule
