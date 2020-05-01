@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.worldsnas.domain.helpers

import com.worldsnas.domain.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.typeInfo
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.URLProtocol
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.use
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Suppress("MemberVisibilityCanBePrivate")
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
        get() = statusCode in 200..299

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
        @Suppress("UNCHECKED_CAST")
        Response.Error(statusCode, response.responseToString()) as Response<T>
    }
}

fun createClient(engine: HttpClientEngine? = null) =
    if (engine == null) {
        HttpClient(clientConfigure)
    } else {
        HttpClient(engine, clientConfigure)
    }

internal const val BASE_URL = "api.themoviedb.org"

private val clientConfigure: HttpClientConfig<*>.() -> Unit = {
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
//    install(Logging) {
//        logger = Logger.DEFAULT
//        level = LogLevel.ALL
//    }
    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            parameter("api_key", BuildKonfig.API_KEY)
        }
    }
}

val jsonSerializer = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

suspend inline fun <reified T> parseSuccess(code: Int, response: HttpResponse): Response<T> {
    val type = typeInfo<T>()
    val serializer = type.kotlinType?.let { serializer(it) } ?: type.type.serializer()

    val text = response.responseToString()

    val parsed = jsonSerializer.parse(serializer, text)

    return Response.Success(code, parsed as T)
}

suspend fun HttpResponse.responseToString(): String =
    readText(Charsets.UTF_8)
