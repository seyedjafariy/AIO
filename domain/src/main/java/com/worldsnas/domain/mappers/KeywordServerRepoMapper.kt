package com.worldsnas.domain.mappers

import com.worldsnas.domain.model.repomodel.KeywordRepoModel
import com.worldsnas.domain.model.servermodels.KeywordServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class KeywordServerRepoMapper @Inject constructor(
) : Mapper<KeywordServerModel, KeywordRepoModel> {
    override fun map(item: KeywordServerModel): KeywordRepoModel =
        KeywordRepoModel(
            item.id,
            item.name
        )
}