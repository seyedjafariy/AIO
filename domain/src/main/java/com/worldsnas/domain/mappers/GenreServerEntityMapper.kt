package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.GenreEntity
import com.worldsnas.domain.model.servermodels.GenreServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class GenreServerEntityMapper @Inject constructor(
) : Mapper<GenreServerModel, GenreEntity> {
    override fun map(item: GenreServerModel): GenreEntity =
        GenreEntity(
            item.id,
            item.name
        )
}