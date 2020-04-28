package com.worldsnas.domain.repo.people

import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleAPI {

    @GET("/3/person/popular")
    suspend fun getPopularPeople(
        @Query("page")
        page : Int
    ) : Response<ResultsServerModel<PersonServerModel>>
}