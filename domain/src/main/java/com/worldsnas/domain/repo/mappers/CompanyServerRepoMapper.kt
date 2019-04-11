package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.CompanyRepoModel
import com.worldsnas.domain.servermodels.CompanyServerModel
import com.worldsnas.panther.Mapper
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