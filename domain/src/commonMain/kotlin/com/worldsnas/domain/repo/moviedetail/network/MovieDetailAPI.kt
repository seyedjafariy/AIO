package com.worldsnas.domain.repo.moviedetail.network

import com.worldsnas.domain.helpers.Response
import com.worldsnas.domain.helpers.executeRequest
import com.worldsnas.domain.model.servermodels.MovieServerModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod

interface MovieDetailAPI {

    suspend fun getMovie(
        movieID: Long,
        appendToResponse: String,
        path: String = "3/movie"
    ): Response<MovieServerModel>
}

class MovieDetailAPIImpl(
    private val engine: HttpClientEngine? = null
) : MovieDetailAPI {
    override suspend fun getMovie(
        movieID: Long,
        appendToResponse: String,
        path: String
    ): Response<MovieServerModel> = executeRequest(engine) {
        url {
            method = HttpMethod.Get
            path(listOf(path, movieID.toString()))
            parameter("append_to_response", appendToResponse)
        }
    }
}