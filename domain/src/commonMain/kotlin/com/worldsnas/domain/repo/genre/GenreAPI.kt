package com.worldsnas.domain.repo.genre

import com.worldsnas.domain.helpers.Response
import com.worldsnas.domain.helpers.executeRequest
import com.worldsnas.domain.model.servermodels.GenreListServerModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.HttpMethod

interface GenreAPI {

    suspend fun allMovieGenre(
        endPoint: String = "/genre/movie/list"
    ): Response<GenreListServerModel>
}

class GenreAPIImpl(
    private val engine: HttpClientEngine? = null
) : GenreAPI {
    override suspend fun allMovieGenre(endPoint: String): Response<GenreListServerModel> =
        executeRequest(engine) {
            method = HttpMethod.Get
            url {
                encodedPath = endPoint
            }
        }
}