package com.worldsnas.panther

import io.reactivex.Single

interface Fetcher<in T : Any?, R : Any>{

    fun fetch(param : T) : Single<R>
}