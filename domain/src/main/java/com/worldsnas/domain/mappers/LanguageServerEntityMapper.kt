package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.LanguageEntity
import com.worldsnas.domain.model.servermodels.LanguageServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class LanguageServerEntityMapper @Inject constructor(
): Mapper<LanguageServerModel, LanguageEntity> {
    override fun map(item: LanguageServerModel): LanguageEntity =
        LanguageEntity(
            0,
            item.iso,
            item.name
        )
}