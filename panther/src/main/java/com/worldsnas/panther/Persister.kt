package com.worldsnas.panther

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface Persister<KEY, T> {

    fun observe(param : KEY) : Observable<T>

    fun read(param : KEY) : Single<T>

    fun write(item : T) : Completable
}