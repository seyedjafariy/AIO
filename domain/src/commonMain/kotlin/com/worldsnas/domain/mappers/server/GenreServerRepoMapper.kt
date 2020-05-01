package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.servermodels.GenreServerModel

class GenreServerRepoMapper : Mapper<GenreServerModel, GenreRepoModel> {
    override fun map(item: GenreServerModel): GenreRepoModel =
        GenreRepoModel(
            item.id,
            item.name
        )
}