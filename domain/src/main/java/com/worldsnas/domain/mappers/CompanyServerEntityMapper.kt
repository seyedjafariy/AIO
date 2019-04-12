package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CompanyEntity
import com.worldsnas.domain.servermodels.CompanyServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CompanyServerEntityMapper @Inject constructor(
): Mapper<CompanyServerModel, CompanyEntity> {
    override fun map(item: CompanyServerModel): CompanyEntity =
        CompanyEntity(
            item.id,
            item.logoPath,
            item.name,
            item.originCountry
        )
}