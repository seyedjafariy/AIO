package com.worldsnas.core

sealed class ErrorHolder(
    val code: Int,
    val throwable: Throwable? = null
) {
    data class Message(
        val message: String,
        val status: Int,
        val cause: Throwable? = null
    ) : ErrorHolder(
        status,
        cause
    )

    data class Res(
        val message: Int,
        val status: Int,
        val cause: Throwable? = null
    ) : ErrorHolder(
        status,
        cause
    )

    data class UnAuthorized(
        val message: String,
        val status: Int,
        val cause: Throwable? = null
    ) : ErrorHolder(
        status,
        cause
    )
}
