package com.worldsnas.daggercore.modules.domain

import com.worldsnas.core.Mapper
import com.worldsnas.db.CompleteMovie
import com.worldsnas.db.Genre
import com.worldsnas.db.Movie
import com.worldsnas.domain.mappers.*
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.KeywordRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.repomodel.PersonRepoModel
import com.worldsnas.domain.model.servermodels.KeywordServerModel
import com.worldsnas.domain.model.servermodels.PersonServerModel
import dagger.Module
import dagger.Provides

@Module(includes = [ServerMapperModule::class])
object MappersModule {

    @Provides
    @JvmStatic
    fun bindPersonServerRepoMapper(): Mapper<PersonServerModel, PersonRepoModel> =
        PersonServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindKeywordServerRepoMapper(): Mapper<KeywordServerModel, KeywordRepoModel> =
        KeywordServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindMovieRepoDbMapper(): Mapper<MovieRepoModel, Movie> = MovieRepoDbMapper()

    @Provides
    @JvmStatic
    fun bindMovieDbRepoMapper(): Mapper<Movie, MovieRepoModel> = MovieDbRepoMapper()

    @Provides
    @JvmStatic
    fun bindGenreRepoDBMapper(): Mapper<GenreRepoModel, Genre> = GenreRepoDBMapper()

    @Provides
    @JvmStatic
    fun bindGenreDbRepoMapper(): Mapper<Genre, GenreRepoModel> = GenreDbRepoMapper()

    @Provides
    @JvmStatic
    fun bindCompleteMovieRepoMapper(
        movieMapper: Mapper<Movie, MovieRepoModel>,
        genreMapper: Mapper<Genre, GenreRepoModel>
    ): Mapper<CompleteMovie, MovieRepoModel> = CompleteMovieRepoMapper(
        movieMapper, genreMapper
    )

}
