package com.worldsnas.domain.di

import com.worldsnas.db.CompleteMovie
import com.worldsnas.db.Genre
import com.worldsnas.db.Movie
import com.worldsnas.domain.mappers.*
import com.worldsnas.domain.model.repomodel.*
import com.worldsnas.domain.model.servermodels.*
import com.worldsnas.panther.Mapper
import dagger.Binds
import dagger.Module

@Module(includes = [ServerMapperModule::class])
abstract class MappersModule {

    @Binds
    abstract fun bindPersonServerRepoMapper(mapper: PersonServerRepoMapper):
            Mapper<PersonServerModel, PersonRepoModel>

    @Binds
    abstract fun bindKeywordServerRepoMapper(mapper: KeywordServerRepoMapper):
            Mapper<KeywordServerModel, KeywordRepoModel>

    @Binds
    abstract fun bindMovieRepoDbMapper(mapper: MovieRepoDbMapper):
            Mapper<MovieRepoModel, Movie>

    @Binds
    abstract fun bindMovieDbRepoMapper(mapper: MovieDbRepoMapper):
            Mapper<Movie, MovieRepoModel>

    @Binds
    abstract fun bindGenreRepoDBMapper(mapper: GenreRepoDBMapper):
            Mapper<GenreRepoModel, Genre>

    @Binds
    abstract fun bindGenreDbRepoMapper(mapper: GenreDbRepoMapper):
            Mapper<Genre, GenreRepoModel>

    @Binds
    abstract fun bindCompleteMovieRepoMapper(mapper: CompleteMovieRepoMapper):
            Mapper<CompleteMovie, MovieRepoModel>

}
