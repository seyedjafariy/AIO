package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.servermodels.GenreServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class GenreServerRepoMapper @Inject constructor(
): Mapper<GenreServerModel, GenreRepoModel> {
    override fun map(item: GenreServerModel): GenreRepoModel =
        GenreRepoModel(
            item.id,
            item.name
        )
}