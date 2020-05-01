package com.worldsnas.domain.repo.people

import com.worldsnas.domain.helpers.Response
import com.worldsnas.domain.helpers.executeRequest
import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod

interface PeopleAPI {

    suspend fun getPopularPeople(
        page: Int,
        path: String = "/3/person/popular"
    ): Response<ResultsServerModel<PersonServerModel>>
}

class PeopleAPIImpl(
    private val engine: HttpClientEngine? = null
) : PeopleAPI {
    override suspend fun getPopularPeople(
        page: Int,
        path: String
    ): Response<ResultsServerModel<PersonServerModel>> =
        executeRequest(engine) {
            method = HttpMethod.Get
            url {
                encodedPath = path
                parameter("page", page)
            }
        }
}