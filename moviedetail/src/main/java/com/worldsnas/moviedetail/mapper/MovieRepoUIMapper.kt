package com.worldsnas.moviedetail.mapper

import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.moviedetail.model.MovieUIModel
import com.worldsnas.core.Mapper
import javax.inject.Inject

class MovieRepoUIMapper @Inject constructor(
) : Mapper<MovieRepoModel, MovieUIModel> {
    override fun map(item: MovieRepoModel): MovieUIModel =
        MovieUIModel(
            item.id,
            item.posterPath,
            item.backdropPath,
            item.title,
            item.releaseDate
        )
}