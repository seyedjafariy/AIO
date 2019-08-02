package com.worldsnas.domain.mappers

import com.worldsnas.domain.model.repomodel.PersonRepoModel
import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class PersonServerRepoMapper @Inject constructor(
) : Mapper<PersonServerModel, PersonRepoModel> {
    override fun map(item: PersonServerModel): PersonRepoModel =
        PersonRepoModel(
            item.id,
            item.popularity,
            item.profilePath,
            item.name,
            item.adult
        )
}