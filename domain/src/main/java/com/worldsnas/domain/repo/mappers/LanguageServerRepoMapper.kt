package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.LanguageRepoModel
import com.worldsnas.domain.servermodels.LanguageServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class LanguageServerRepoMapper @Inject constructor(
): Mapper<LanguageServerModel, LanguageRepoModel> {
    override fun map(item: LanguageServerModel): LanguageRepoModel =
        LanguageRepoModel(
            item.iso,
            item.name
        )
}