package com.worldsnas.domain.repo.people

import com.worldsnas.domain.model.servermodels.PersonServerModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface PeopleAPI {

    @GET("/3/person/popular")
    fun getPopularPeople(
    ) : Single<Response<ArrayList<PersonServerModel>>>
}