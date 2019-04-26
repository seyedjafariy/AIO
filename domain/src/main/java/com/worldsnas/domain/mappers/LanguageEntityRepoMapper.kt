package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.LanguageEntity
import com.worldsnas.domain.model.repomodel.LanguageRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class LanguageEntityRepoMapper @Inject constructor(
): Mapper<LanguageEntity, LanguageRepoModel> {
    override fun map(item: LanguageEntity): LanguageRepoModel =
        LanguageRepoModel(
            item.id,
            item.iso,
            item.name
        )
}