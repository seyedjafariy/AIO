package com.worldsnas.core

import io.reactivex.Observable
import io.reactivex.annotations.SchedulerSupport
import java.util.concurrent.TimeUnit
import javax.annotation.CheckReturnValue

fun <T> delayEvent(immediate : T, delayed : T) =
    Observable.just(delayed)
        .delay(3, TimeUnit.SECONDS)
        .startWith(immediate)

@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any, U : Any> Observable<T>.notOfType(clazz: Class<U>): Observable<T> {
    checkNotNull(clazz) { "clazz is null" }
    return filter { !clazz.isInstance(it) }
}
