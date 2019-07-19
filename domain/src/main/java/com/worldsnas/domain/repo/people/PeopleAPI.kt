package com.worldsnas.domain.repo.people

import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleAPI {

    @GET("/3/person/popular")
    fun getPopularPeople(
        @Query("page")
        page : Int
    ) : Single<Response<ResultsServerModel<PersonServerModel>>>
}