package com.worldsnas.domain.repo.search

import com.worldsnas.domain.helpers.Response
import com.worldsnas.domain.helpers.executeRequest
import com.worldsnas.domain.model.servermodels.KeywordServerModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod

interface SearchAPI {

    suspend fun searchMovie(
        query: String,
        page: Int,
        includeAdult: Boolean,
        path : String = "/3/search/movie"
    ): Response<ResultsServerModel<MovieServerModel>>

    suspend fun searchKeywords(
        query: String,
        page: Int,
        includeAdult: Boolean,
        path : String = "/3/search/keyword"
    ) : Response<ResultsServerModel<KeywordServerModel>>
}

class SearchAPIImpl(
    private val engine: HttpClientEngine? = null
) : SearchAPI {
    override suspend fun searchMovie(
        query: String,
        page: Int,
        includeAdult: Boolean,
        path: String
    ): Response<ResultsServerModel<MovieServerModel>> = executeRequest(engine) {
        method = HttpMethod.Get
        url {
            encodedPath = path
            parameter("page", page)
            parameter("query", query)
            parameter("include_adult", includeAdult)
        }
    }

    override suspend fun searchKeywords(
        query: String,
        page: Int,
        includeAdult: Boolean,
        path: String
    ): Response<ResultsServerModel<KeywordServerModel>> = executeRequest(engine) {
        method = HttpMethod.Get
        url {
            encodedPath = path
            parameter("page", page)
            parameter("query", query)
            parameter("include_adult", includeAdult)
        }
    }
}