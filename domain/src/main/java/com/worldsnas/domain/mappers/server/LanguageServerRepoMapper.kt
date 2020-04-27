package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.model.repomodel.LanguageRepoModel
import com.worldsnas.domain.model.servermodels.LanguageServerModel
import com.worldsnas.core.Mapper
import javax.inject.Inject

class LanguageServerRepoMapper @Inject constructor(
): Mapper<LanguageServerModel, LanguageRepoModel> {
    override fun map(item: LanguageServerModel): LanguageRepoModel =
        LanguageRepoModel(
            0,
            item.iso,
            item.name
        )
}