package com.worldsnas.panther

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Fetcher<T : Any?, R : Any>{

    fun fetch(param : T) : Flow<Response<R>>
}