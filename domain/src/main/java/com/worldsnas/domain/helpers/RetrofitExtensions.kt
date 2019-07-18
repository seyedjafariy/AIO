package com.worldsnas.domain.helpers

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.R
import com.worldsnas.domain.model.servermodels.error.ErrorServerModel
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.Response

val Response<*>.isNotSuccessful
    get() = !isSuccessful

fun Response<*>.getErrorServerModel(): ErrorServerModel =
    errorBody()
        ?.use { it.string() }
        ?.let {
            try {
                JSONObject(it)
            } catch (e: Exception) {
                null
            }
        }
        .let {
            val code = it?.optInt("status_code", this.code()) ?: 0
            val message = it?.optString("status_message", this.message() ?: "") ?: ""

            ErrorServerModel(message, code)
        }

fun Response<*>.getErrorRepoModel(): ErrorHolder =
    if (isSuccessful && body() == null)
        ErrorHolder.Res(R.string.error_no_item_received, code())
    else
        errorBody()
            ?.use { it.string() }
            ?.let {
                try {
                    JSONObject(it)
                } catch (e: Exception) {
                    null
                }
            }
            .let {
                val code = it?.optInt("status_code", this.code()) ?: 0
                val message = it?.optString("status_message", this.message() ?: "") ?: ""

                ErrorHolder.Message(message, code)
            }

fun <T> Single<Response<T>>.errorHandler(times: Int = 3): Single<Response<T>> =
    retry { retried, _ ->
        retried <= times
    }.onErrorReturn {
        createErrorResponse(it)
    }

fun <T, U> Single<Response<T>>.eitherError(map: (T) -> U):
        Single<Either<U, ErrorHolder>> =
    toObservable()
        .publish { publish ->
            Observable.merge(
                publish
                    .filter { it.isNotSuccessful || it.body() == null }
                    .map { it.getErrorRepoModel().right() },
                publish
                    .filter { it.isSuccessful && it.body() != null }
                    .map {
                        map(it.body()!!).left()
                    }
            )
        }
        .firstOrError()