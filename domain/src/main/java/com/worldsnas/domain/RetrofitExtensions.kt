package com.worldsnas.domain

import com.worldsnas.domain.repomodel.ErrorRepoModel
import com.worldsnas.domain.servermodels.error.ErrorServerModel
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

fun Response<*>.getErrorRepoModel(): ErrorRepoModel =
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

            ErrorRepoModel(message, code)
        }