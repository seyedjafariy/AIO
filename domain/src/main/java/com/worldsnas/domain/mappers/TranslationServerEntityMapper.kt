package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.TranslationEntity
import com.worldsnas.domain.model.servermodels.TranslationServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class TranslationServerEntityMapper @Inject constructor(
) : Mapper<TranslationServerModel, TranslationEntity> {
    override fun map(item: TranslationServerModel): TranslationEntity =
        TranslationEntity(
            0,
            item.iso_3166_1,
            item.iso_639_1,
            item.name,
            item.englishName,
            item.data?.title ?: "",
            item.data?.overview ?: "",
            item.data?.homePage ?: ""
        )
}