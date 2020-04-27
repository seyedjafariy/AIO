package com.worldsnas.domain.mappers

import com.worldsnas.db.CompleteMovie
import com.worldsnas.db.Genre
import com.worldsnas.db.Movie
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.core.Mapper
import javax.inject.Inject

class CompleteMovieRepoMapper @Inject constructor(
    private val movieMapper: Mapper<Movie, MovieRepoModel>,
    private val genreMapper: Mapper<Genre, GenreRepoModel>
) : Mapper<CompleteMovie, MovieRepoModel> {
    override fun map(item: CompleteMovie): MovieRepoModel =
        movieMapper.map(item.movie).copy(
            similar = item.similars.map {
                movieMapper.map(it)
            },
            recommendations = item.recommended.map {
                movieMapper.map(it)
            },
            genres = item.genres.map {
                genreMapper.map(it)
            }
        )
}