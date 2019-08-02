@file:JvmName("RetrofitErrorUtils")
package com.worldsnas.domain.helpers

import android.telephony.IccOpenLogicalChannelResponse.STATUS_NO_SUCH_ELEMENT
import com.squareup.moshi.JsonDataException
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun getServerErrorStatusCode(throwable: Throwable): Int = when (throwable) {
    is JsonDataException -> STATUS_JSON_MALFORMED
    is TimeoutException -> STATUS_TIMEOUT
    is InterruptedIOException -> STATUS_INTERRUPTED
    is ConnectException -> STATUS_FAILED_CONNECT
    is NoSuchElementException -> STATUS_NO_SUCH_ELEMENT
    is IllegalArgumentException -> STATUS_ILLEGAL_ARGUMENT
    is UnknownHostException -> STATUS_UNKNOWN_HOST
    else -> STATUS_UNKNOWN
}

fun getServerErrorBody(throwable: Throwable): ResponseBody {
    val type = "text".toMediaTypeOrNull()

    val message: String = when (throwable) {
        is JsonDataException -> "Malformed Json"
        is TimeoutException -> "TimeOut"
        is InterruptedIOException -> "Intrupted Connection"
        is ConnectException -> "Connection Failure"
        is NoSuchElementException -> "No Element Found"
        is IllegalArgumentException -> "Wrong Argument"
        else -> "Error"
    }

    return message.toResponseBody(type)
}

fun <T> createErrorResponse(throwable: Throwable): Response<T> {
    val status = getServerErrorStatusCode(throwable)
    val responseBody = getServerErrorBody(throwable)
    return Response.error(status, responseBody)
}

fun getThrowableErrorString(t: Throwable) =
    getStatusCodeErrorString(getServerErrorStatusCode(t))

//@StringRes
fun getStatusCodeErrorString(code: Int) = when (code) {
    STATUS_UNAUTHORIZED -> R.string.error_unAuthorized
    STATUS_NOT_FOUND -> R.string.error_not_found
    STATUS_BAD_REQUEST -> R.string.error_internal
    STATUS_SERVER_FAILURE -> R.string.error_server_failure
    STATUS_JSON_MALFORMED -> R.string.error_data_receive
    STATUS_TIMEOUT -> R.string.error_time_out
    STATUS_INTERRUPTED -> R.string.error_connection_failure
    STATUS_FAILED_CONNECT -> R.string.error_connection_failure
    STATUS_NO_SUCH_ELEMENT -> R.string.error_element_not_found
    STATUS_ILLEGAL_ARGUMENT -> R.string.error_illegal_argument
    STATUS_UNKNOWN_HOST -> R.string.error_unknown_host
    STATUS_TRANSMISSION_FAILED -> R.string.error_transmission
    STATUS_UNKNOWN -> R.string.error_unknown
    else -> R.string.error_server
}

fun getStatusCodeErrorHolder(code: Int, errorMessage: String): ErrorHolder =
    if (code == STATUS_UNAUTHORIZED) {
        getUnAuthorizedMessage(errorMessage, code)
    } else {
        ErrorHolder.Res(getStatusCodeErrorString(code), code)
    }

fun getUnAuthorizedMessage(errorMessage: String, code: Int): ErrorHolder =
    if (errorMessage.isNotEmpty()) {
        ErrorHolder.UnAuthorized(errorMessage, code)
    } else {
        ErrorHolder.UnAuthorized("you are unAuthorized to use this service", code)
    }


fun <T> Response<T>.getErrorHolder(): ErrorHolder {
    val errorMessage = errorBody()?.use { it.string() }?.genericErrorParseFirstError() ?: ""
    return if (errorMessage.isEmpty() || code() == STATUS_UNAUTHORIZED) {
        getStatusCodeErrorHolder(code(), errorMessage)
    } else {
        ErrorHolder.Message(errorMessage, code())
    }
}

fun getResponseErrorHolder(response: Response<*>): ErrorHolder {
    val errorMessage = response.errorBody()?.use { err -> err.string() }?.genericErrorParseFirstError()
        ?: ""
    return if (errorMessage.isEmpty() || response.code() == STATUS_UNAUTHORIZED) {
        getStatusCodeErrorHolder(response.code(), errorMessage)
    } else {
        ErrorHolder.Message(errorMessage, response.code())
    }
}

fun String.genericParse(): Map<String, List<String>> {
    val jsonObj = JSONObject(this)
    val dataObj = jsonObj.getJSONObject("data")
    val map = mutableMapOf<String, List<String>>()
    dataObj.keys().forEach { key ->
        val keyArr = dataObj.getJSONArray(key)
        val errors =
            if (keyArr.length() > 0) {
                mutableListOf()
            } else {
                emptyList<String>()
            }

        for (index in 0 until keyArr.length()) {
            errors + keyArr.getString(index)
        }
        map[key] = errors
    }

    return map
}

fun String.genericErrorParseFirstError(): String {
    try {
        val jsonObj = JSONObject(this)

        if (jsonObj.has("msg")) {
            return jsonObj.getString("msg")
        }

        if (jsonObj.has("detail")) {
            return jsonObj.getString("detail")
        }


        if (jsonObj.has("data")) {
            val dataObj = jsonObj.get("data")
            if (dataObj is JSONObject) {
                val dataKeys = dataObj.keys()

                dataKeys.forEach { key ->
                    val keyObj = dataObj.get(key)

                    if (keyObj is JSONArray) {
                        val keyArr = dataObj.getJSONArray(key)
                        if (keyArr.length() > 0) {
                            return keyArr.getString(0)
                        }
                    } else if (key == "detail" && keyObj is String) {
                        return keyObj
                    }
                }
            } else if (dataObj is JSONArray) {
                if (dataObj.length() > 0)
                    return dataObj.getString(0)
            }
        } else {
            jsonObj.keys().forEach { key ->
                val keyObj = jsonObj.get(key)

                if (keyObj is JSONArray) {
                    val keyArr = jsonObj.getJSONArray(key)
                    if (keyArr.length() > 0) {
                        return keyArr.getString(0)
                    }
                } else if (keyObj is String) {
                    return keyObj
                }
            }
        }

    } catch (e: JSONException) {
        Timber.e(e, "error_parser, failed to parse generic response")
        return ""
    }

    return ""
}

