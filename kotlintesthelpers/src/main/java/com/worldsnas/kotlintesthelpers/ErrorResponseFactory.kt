package com.worldsnas.kotlintesthelpers

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.intellij.lang.annotations.Language
import retrofit2.Response

@Language("JSON")
fun createOkHttpErrorResponseJson(code : Int = 400, message: String = "") =
        "{\n  \"status_code\" : $code,\n  \"status_message\" : $message\n}"

fun createOkHttpErrorResponse(code : Int = 400, message: String = ""): ResponseBody =
        ResponseBody.create(MediaType.parse(""), createOkHttpErrorResponseJson(code, message))

fun <T> createRetrofitErrorResponse(code : Int = 400, message: String = ""): Response<T> =
        Response.error<T>(
                code,
                createOkHttpErrorResponse(code, message))