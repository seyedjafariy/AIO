package com.worldsnas.panther

import retrofit2.Response

interface Fetcher<T : Any?, R : Any>{

    suspend fun fetch(param : T) : Response<R>
}