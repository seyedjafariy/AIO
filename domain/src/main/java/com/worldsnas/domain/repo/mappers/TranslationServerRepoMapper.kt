package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.TranslationRepoModel
import com.worldsnas.domain.servermodels.TranslationServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class TranslationServerRepoMapper @Inject constructor(
) : Mapper<TranslationServerModel, TranslationRepoModel> {
    override fun map(item: TranslationServerModel): TranslationRepoModel =
        TranslationRepoModel(
            item.iso_3166_1,
            item.iso_639_1,
            item.name,
            item.englishName,
            item.data?.title ?: "",
            item.data?.overview ?: "",
            item.data?.homePage ?: ""
        )
}