package com.worldsnas.domain.di

import com.worldsnas.domain.repo.genre.GenreRepoModule
import com.worldsnas.domain.repo.home.HomeRepoModule
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepoModule
import com.worldsnas.domain.repo.people.PeopleRepoModule
import com.worldsnas.domain.repo.search.SearchRepoModule
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
