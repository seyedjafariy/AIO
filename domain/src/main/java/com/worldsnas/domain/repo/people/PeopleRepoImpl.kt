package com.worldsnas.domain.repo.people

import arrow.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.helpers.eitherError
import com.worldsnas.domain.model.repomodel.PersonRepoModel
import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.panther.Mapper
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import javax.inject.Inject

class PeopleRepoImpl @Inject constructor(
    private val fetcher: RFetcher<Unit, ArrayList<PersonServerModel>>,
    private val personMapper: Mapper<PersonServerModel, PersonRepoModel>
) : PeopleRepo {
    override fun fetchPeople(): Single<Either<List<PersonRepoModel>, ErrorHolder>> =
        fetcher.fetch(Unit)
            .eitherError {
                it.map { person ->
                    personMapper
                        .map(person)
                }
            }
}