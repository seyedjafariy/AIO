package com.worldsnas.domain.repo.people

import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.PersonRepoModel
import com.worldsnas.domain.helpers.eitherError
import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.core.Mapper
import com.worldsnas.core.suspendToFlow
import com.worldsnas.domain.helpers.errorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


interface PeopleRepo {
    fun fetchPeople(page: Int): Flow<Either<ErrorHolder, List<PersonRepoModel>>>
    fun cachedPeople(): Flow<List<PersonRepoModel>>
}

class PeopleRepoImpl @Inject constructor(
    private val api: PeopleAPI,
    private val personMapper: Mapper<PersonServerModel, PersonRepoModel>
) : PeopleRepo {
    private var people: MutableList<PersonRepoModel> = mutableListOf()

    override fun fetchPeople(page: Int): Flow<Either<ErrorHolder, List<PersonRepoModel>>> =
        suspendToFlow {
            api.getPopularPeople(1)
        }
            .errorHandler()
            .eitherError {
                it
                    .list
                    .map { person ->
                        personMapper
                            .map(person)
                    }
                    .also { personList ->
                        if (page == 1) {
                            people = ArrayList(personList)
                        } else {
                            people.addAll(personList)
                        }
                    }
            }

    override fun cachedPeople(): Flow<List<PersonRepoModel>> =
        flowOf(people)
}