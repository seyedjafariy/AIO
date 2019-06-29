package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CountryEntity
import com.worldsnas.domain.model.servermodels.CountryServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CountryServerEntityMapper @Inject constructor(
) : Mapper<CountryServerModel, CountryEntity> {
    override fun map(item: CountryServerModel): CountryEntity =
        CountryEntity(
            0,
            item.iso,
            item.name
        )
}