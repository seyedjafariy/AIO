package com.worldsnas.domain.mappers

import com.worldsnas.db.Movie
import com.worldsnas.domain.helpers.toDate
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class MovieServerDbMapper @Inject constructor(
) : Mapper<MovieServerModel, Movie> {
    override fun map(item: MovieServerModel): Movie =
        Movie.Impl(
            item.id,
            item.originalTitle,
            item.backdropPath,
            item.posterPath,
            item.releaseDate.toDate(),
            "",
            false
        )
}