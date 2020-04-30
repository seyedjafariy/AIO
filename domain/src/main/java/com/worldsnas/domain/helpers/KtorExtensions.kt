package com.worldsnas.domain.helpers

import com.worldsnas.core.*
import com.worldsnas.domain.model.servermodels.error.ErrorServerModel
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonException

fun Response<*>.getErrorRepoModel(): ErrorHolder =
    (this as Response.Error)
        .let {
            Json.parse(ErrorServerModel.serializer(), it.body)
        }
        .let {
            ErrorHolder.Message(it.message, it.code)
        }

fun <T> Flow<Response<T>>.errorHandler(times: Long = 3): Flow<Response<T>> =
    retry(times) { throwable ->
        throwable is IOException
    }.catch { t: Throwable ->
        emit(createThrowableErrorResponse(t))
    }

fun <T> createThrowableErrorResponse(throwable: Throwable): Response<T> {
    val status = getServerErrorStatusCode(throwable)
    val errorMessage = getThrowableErrorMessage(throwable)
    return Response.Error(status, errorMessage) as Response<T>
}

fun getThrowableErrorMessage(throwable: Throwable): String =
    when (throwable) {
        is JsonException -> "Malformed Json"
//        is TimeoutException -> "TimeOut"
//        is InterruptedIOException -> "Intrupted Connection"
//        is ConnectException -> "Connection Failure"
        is NoSuchElementException -> "No Element Found"
        is IllegalArgumentException -> "Wrong Argument"
        else -> "Error"
    }

fun <T, U> Flow<Response<T>>.eitherError(map: (T) -> U): Flow<Either<ErrorHolder, U>> =
    listMerge(
        {
            filter { it.isNotSuccessful }
                .map { it.getErrorRepoModel().left() }
        },
        {
            filter { it.isSuccessful }
                .map {
                    it as Response.Success<T>
                    map(it.data).right()
                }
        }
    )
