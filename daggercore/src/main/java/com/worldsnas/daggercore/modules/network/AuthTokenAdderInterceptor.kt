package com.worldsnas.daggercore.modules.network

import com.worldsnas.daggercore.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenAdderInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url
        val url = originalUrl
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request.newBuilder().url(url).build())
            .also { response ->
                response
                    .newBuilder()
                    .request(
                        response.request
                            .newBuilder()
                            .url(originalUrl)
                            .build()
                    )
                    .build()
            }
    }
}