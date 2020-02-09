package com.worldsnas.domain.mappers

import com.worldsnas.db.Movie
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class MovieRepoDbMapper @Inject constructor(
) : Mapper<MovieRepoModel, Movie> {
    override fun map(item: MovieRepoModel): Movie =
        Movie.Impl(
            item.id,
            item.originalTitle,
            item.backdropPath,
            item.posterPath,
            item.releaseDate,
            "",
            false
        )
}