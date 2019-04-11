package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CountryEntity
import com.worldsnas.domain.repomodel.CountryRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CountryEntityRepoMapper @Inject constructor(
) : Mapper<CountryEntity, CountryRepoModel> {
    override fun map(item: CountryEntity): CountryRepoModel =
        CountryRepoModel(
            item.id,
            item.iso,
            item.name
        )
}