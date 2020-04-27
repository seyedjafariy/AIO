package com.worldsnas.home.mapper

import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.core.Mapper
import com.worldsnas.view_component.Movie
import javax.inject.Inject

class MovieRepoUIMapper @Inject constructor(
) : Mapper<MovieRepoModel, Movie> {
    override fun map(item: MovieRepoModel): Movie =
        Movie(
            item.id,
            item.posterPath,
            item.backdropPath,
            item.title,
            item.releaseDate
        )
}