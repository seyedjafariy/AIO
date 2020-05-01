package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.CompanyRepoModel
import com.worldsnas.domain.model.servermodels.CompanyServerModel

class CompanyServerRepoMapper : Mapper<CompanyServerModel, CompanyRepoModel> {
    override fun map(item: CompanyServerModel): CompanyRepoModel =
        CompanyRepoModel(
            item.id,
            item.logoPath,
            item.name,
            item.originCountry
        )
}