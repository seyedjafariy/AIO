package com.worldsnas.core

sealed class ErrorHolder {
    data class Message(
        val message: String,
        val code: Int
    ) : ErrorHolder()

    data class Res(
        val message: Int,
        val code: Int
    ) : ErrorHolder()

    data class UnAuthorized(
        val message: String,
        val code: Int
    ) : ErrorHolder()
}
