package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.TranslationEntity
import com.worldsnas.domain.repomodel.TranslationRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class TranslationEntityRepoMapper @Inject constructor(
) : Mapper<TranslationEntity, TranslationRepoModel> {
    override fun map(item: TranslationEntity): TranslationRepoModel =
        TranslationRepoModel(
            item.id,
            item.iso_3166_1,
            item.iso_639_1,
            item.name,
            item.englishName,
            item.title,
            item.overview,
            item.homePage
        )
}