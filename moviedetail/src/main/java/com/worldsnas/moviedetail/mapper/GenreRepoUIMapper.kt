package com.worldsnas.moviedetail.mapper

import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.moviedetail.model.GenreUIModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class GenreRepoUIMapper @Inject constructor(
): Mapper<GenreRepoModel, GenreUIModel> {
    override fun map(item: GenreRepoModel): GenreUIModel =
            GenreUIModel(
                item.id,
                item.name
            )
}