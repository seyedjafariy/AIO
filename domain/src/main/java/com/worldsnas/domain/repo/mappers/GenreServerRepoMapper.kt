package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.GenreRepoModel
import com.worldsnas.domain.servermodels.GenreServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class GenreServerRepoMapper @Inject constructor(
) : Mapper<GenreServerModel, GenreRepoModel> {
    override fun map(item: GenreServerModel): GenreRepoModel =
        GenreRepoModel(
            item.id,
            item.name
        )
}