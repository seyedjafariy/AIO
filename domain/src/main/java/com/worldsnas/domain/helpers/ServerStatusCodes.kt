@file:JvmName("ServerStatusCodes")

package com.worldsnas.domain.helpers


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



