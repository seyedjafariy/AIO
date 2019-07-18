package com.worldsnas.domain.repo.people

import arrow.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.PersonRepoModel
import io.reactivex.Single

interface PeopleRepo {
    fun fetchPeople(): Single<Either<List<PersonRepoModel>, ErrorHolder>>
}