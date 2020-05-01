package com.worldsnas.domain.mappers

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.KeywordRepoModel
import com.worldsnas.domain.model.servermodels.KeywordServerModel

class KeywordServerRepoMapper : Mapper<KeywordServerModel, KeywordRepoModel> {
    override fun map(item: KeywordServerModel): KeywordRepoModel =
        KeywordRepoModel(
            item.id,
            item.name
        )
}