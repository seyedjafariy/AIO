package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.model.repomodel.CompanyRepoModel
import com.worldsnas.domain.model.servermodels.CompanyServerModel
import com.worldsnas.core.Mapper
import javax.inject.Inject

class CompanyServerRepoMapper @Inject constructor(
): Mapper<CompanyServerModel, CompanyRepoModel> {
    override fun map(item: CompanyServerModel): CompanyRepoModel =
        CompanyRepoModel(
            item.id,
            item.logoPath,
            item.name,
            item.originCountry
        )
}