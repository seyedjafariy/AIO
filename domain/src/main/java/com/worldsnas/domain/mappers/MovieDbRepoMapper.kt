package com.worldsnas.domain.mappers

import com.worldsnas.db.Movie
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class MovieDbRepoMapper @Inject constructor(
): Mapper<Movie, MovieRepoModel> {
    override fun map(item: Movie): MovieRepoModel =
        MovieRepoModel(
            item.id,
            backdropPath = item.backdropImage ?: "",
            posterPath = item.posterImage ?: "",
            title = item.title,
            releaseDate = item.release_date
        )
}