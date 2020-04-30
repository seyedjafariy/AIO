package com.worldsnas.domain.helpers

import com.worldsnas.domain.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.typeInfo
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.util.toByteArray
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

sealed class Response<T>(
    val statusCode: Int
) {
    data class Success<K>(
        val status: Int,
        val data: K
    ) : Response<K>(status)

    data class Error(
        val status: Int,
        val body: String
    ) : Response<Nothing>(status)

    val isSuccessful: Boolean
        get() = 300 < statusCode && statusCode >= 200

    val isNotSuccessful: Boolean
        get() = !isSuccessful
}


//pass null when on other platforms
suspend inline fun <reified T> executeRequest(
    engine: HttpClientEngine? = null,
    block: HttpRequestBuilder.() -> Unit
): Response<T> {
    val response = createClient(engine).use { it.request<HttpResponse>(block) }

    val statusCode = response.status.value

    return if (statusCode in 200..299) {
        parseSuccess(statusCode, response)
    } else {
        Response.Error(statusCode, response.responseToString()) as Response<T>
    }
}

fun createClient(engine: HttpClientEngine? = null) =
    if (engine == null) {
        HttpClient(clientConfigure)
    } else {
        HttpClient(engine, clientConfigure)
    }

internal const val BASE_URL = "https://api.themoviedb.org"

private val clientConfigure: HttpClientConfig<*>.() -> Unit = {
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
    defaultRequest {
        host = BASE_URL
        header("api_key", BuildConfig.API_KEY)
    }
}

suspend inline fun <reified T> parseSuccess(code: Int, response: HttpResponse): Response<T> {
    val type = typeInfo<T>()
    val serializer = type.kotlinType?.let { serializer(it) } ?: type.type.serializer()

    val text = response.responseToString()

    val parsed = Json.parse(serializer, text)

    return Response.Success(code, parsed as T)
}

suspend fun HttpResponse.responseToString() =
    String(content.toByteArray())
