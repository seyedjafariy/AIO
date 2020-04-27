package com.worldsnas.base

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@Suppress("RedundantRequireNotNullCall")
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any, U : Any> Observable<T>.notOfType(clazz: Class<U>): Observable<T> {
    checkNotNull(clazz) { "clazz is null" }
    return filter { !clazz.isInstance(it) }
}

fun <T : Any> delayEvent(immediate : T, delayed : T): Observable<T> =
    Observable.just(delayed)
        .delay(3, TimeUnit.SECONDS)
        .startWith(immediate)
