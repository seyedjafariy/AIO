package com.worldsnas.search.mapper

import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.core.Mapper
import com.worldsnas.search.model.MovieUIModel

class MovieRepoUIMapper(
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