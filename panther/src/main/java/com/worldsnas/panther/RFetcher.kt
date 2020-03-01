package com.worldsnas.panther

import io.reactivex.Single
import retrofit2.Response

interface RFetcher<T : Any?, R : Any>{
    fun fetch(param : T) : Single<Response<R>>
}