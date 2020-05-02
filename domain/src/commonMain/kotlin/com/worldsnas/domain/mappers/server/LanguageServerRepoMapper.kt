package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.LanguageRepoModel
import com.worldsnas.domain.model.servermodels.LanguageServerModel

class LanguageServerRepoMapper : Mapper<LanguageServerModel, LanguageRepoModel> {
    override fun map(item: LanguageServerModel): LanguageRepoModel =
        LanguageRepoModel(
            0,
            item.iso?:"",
            item.name
        )
}