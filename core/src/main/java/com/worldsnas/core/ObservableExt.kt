package com.worldsnas.core

import io.reactivex.Observable
import io.reactivex.annotations.SchedulerSupport
import java.util.concurrent.TimeUnit

fun <T : Any> delayEvent(immediate : T, delayed : T): Observable<T> =
    Observable.just(delayed)
        .delay(3, TimeUnit.SECONDS)
        .startWith(immediate)

@Suppress("RedundantRequireNotNullCall")
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any, U : Any> Observable<T>.notOfType(clazz: Class<U>): Observable<T> {
    checkNotNull(clazz) { "clazz is null" }
    return filter { !clazz.isInstance(it) }
}
