package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.repomodel.CountryRepoModel
import com.worldsnas.domain.servermodels.CountryServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CountryServerRepoMapper @Inject constructor(
): Mapper<CountryServerModel, CountryRepoModel> {
    override fun map(item: CountryServerModel): CountryRepoModel =
        CountryRepoModel(
            0,
            item.iso,
            item.name
        )
}