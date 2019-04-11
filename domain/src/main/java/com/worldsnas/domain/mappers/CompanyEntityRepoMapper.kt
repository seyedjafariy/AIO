package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CompanyEntity
import com.worldsnas.domain.repomodel.CompanyRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CompanyEntityRepoMapper @Inject constructor(
): Mapper<CompanyEntity, CompanyRepoModel> {
    override fun map(item: CompanyEntity): CompanyRepoModel =
        CompanyRepoModel(
            item.id,
            item.logoPath,
            item.name,
            item.originCountry
        )
}