package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.GenreEntity
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class GenreEntityRepoMapper @Inject constructor(
) : Mapper<GenreEntity, GenreRepoModel> {
    override fun map(item: GenreEntity): GenreRepoModel =
        GenreRepoModel(
            item.id,
            item.name
        )
}