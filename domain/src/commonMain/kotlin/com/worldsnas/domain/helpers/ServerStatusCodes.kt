package com.worldsnas.domain.helpers

import io.ktor.http.URLParserException
import kotlinx.serialization.json.JsonException


//server status codes
const val STATUS_UNAUTHORIZED = 401
const val STATUS_NOT_FOUND = 404
const val STATUS_BAD_REQUEST = 400
const val STATUS_SERVER_FAILURE = 500


//in house status codes
const val STATUS_INTERNAL_ERROR = 40000
const val STATUS_JSON_MALFORMED = 40001
const val STATUS_TIMEOUT = 40002
const val STATUS_INTERRUPTED = 40003
const val STATUS_FAILED_CONNECT = 40004
const val STATUS_NO_SUCH_ELEMENT = 40005
const val STATUS_ILLEGAL_ARGUMENT = 40006
const val STATUS_UNKNOWN_HOST = 40007
const val STATUS_TRANSMISSION_FAILED = 40150
const val STATUS_UNKNOWN = 40099


fun getStatusCodeErrorString(code: Int) = when (code) {
    STATUS_UNAUTHORIZED -> "UnAuthorized"
    STATUS_NOT_FOUND -> "Not Found"
    STATUS_BAD_REQUEST -> "Internal Error"
    STATUS_SERVER_FAILURE -> "Server Failed"
    STATUS_JSON_MALFORMED -> "Wrong Data Received"
    STATUS_TIMEOUT -> "Connection TimeOut"
    STATUS_INTERRUPTED -> "Connection Failure"
    STATUS_FAILED_CONNECT -> "Connection Failure"
    STATUS_NO_SUCH_ELEMENT -> "Item Not Found"
    STATUS_ILLEGAL_ARGUMENT -> "Wrong Parameter"
    STATUS_UNKNOWN_HOST -> "Could Not Find Host"
    STATUS_TRANSMISSION_FAILED -> "Transmission Failed"
    STATUS_UNKNOWN -> "Unknown Error"
    else -> "Server Error"
}

fun getServerErrorStatusCode(throwable: Throwable): Int = when (throwable) {
    is JsonException -> STATUS_JSON_MALFORMED
//    is TimeoutException -> STATUS_TIMEOUT
//    is InterruptedIOException -> STATUS_INTERRUPTED
//    is ConnectException -> STATUS_FAILED_CONNECT
    is URLParserException -> STATUS_INTERNAL_ERROR
    is NoSuchElementException -> STATUS_NO_SUCH_ELEMENT
    is IllegalArgumentException -> STATUS_ILLEGAL_ARGUMENT
//    is UnknownHostException -> STATUS_UNKNOWN_HOST
    else -> STATUS_UNKNOWN
}
