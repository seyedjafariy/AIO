package com.worldsnas.domain.repo.home

import com.worldsnas.domain.helpers.Response
import com.worldsnas.domain.helpers.executeRequest
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod

interface HomeAPI {

    suspend fun getLatestMovie(
        finalDate: String,
        page: Int,
        path: String = "/3/discover/movie?include_video=false&include_adult=false&sort_by=release_date.desc"
    ): Response<ResultsServerModel<MovieServerModel>>

    suspend fun getTrendingMovie(
        path: String = "/3/trending/movie/day"
    ): Response<ResultsServerModel<MovieServerModel>>
}

class HomeAPIImpl(
    private val engine: HttpClientEngine? = null
) : HomeAPI {
    override suspend fun getLatestMovie(
        finalDate: String,
        page: Int,
        path: String
    ): Response<ResultsServerModel<MovieServerModel>> =
        executeRequest(engine) {
            url {
                method = HttpMethod.Get
                encodedPath = path
                parameter("page", page)
                parameter("release_date.lte", finalDate)
            }
        }

    override suspend fun getTrendingMovie(path: String): Response<ResultsServerModel<MovieServerModel>> =
        executeRequest(engine) {
            url {
                method = HttpMethod.Get
                encodedPath = path
            }
        }
}