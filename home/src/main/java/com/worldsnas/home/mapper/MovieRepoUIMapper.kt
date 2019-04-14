package com.worldsnas.home.mapper

import com.worldsnas.domain.repomodel.MovieRepoModel
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.panther.Mapper
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