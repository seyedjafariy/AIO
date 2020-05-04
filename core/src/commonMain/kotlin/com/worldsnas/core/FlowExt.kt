package com.worldsnas.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.reflect.KClass

inline fun <reified R> Flow<*>.ofType() = transform {
    if (R::class.isInstance(it)) {
        emit(it as R)
    }
}

fun <T, U : Any> Flow<T>.noOfType(u : KClass<U>): Flow<T> =
    filter { u.isInstance(it) }

fun <T> suspendToFlow(block: suspend () -> T): Flow<T> = flow {
    emit(block())
}

fun <T, U> Flow<T>.listMerge(vararg blocks: Flow<T>.() -> Flow<U>) =
    blocks.map { block ->
        block(this)
    }.merge()

fun <T> Flow<T>.ifEmptyEmit(item: T): Flow<T> {
    return flow {
        var emitted = false
        collect { value ->
            emitted = true
            emit(value)
        }

        if (!emitted) {
            emit(item)
        }
    }
}

fun <T> Flow<T>.toListFlow(): Flow<List<T>> = flow {
    val finalList = mutableListOf<T>()
    collect { value ->
        finalList.add(value)
    }
    emit(finalList.toList())
}

fun <T> Flow<List<T>>.mergeIterable(): Flow<T> =
    flatMapMerge {
        it.asFlow()
    }

fun <T> Flow<List<T>>.concatIterable(): Flow<T> =
    flatMapConcat {
        it.asFlow()
    }

fun <T> delayedFlow(immediate: T, delayed: T): Flow<T> =
    flow {
        emit(immediate)
        delay(3000)
        emit(delayed)
    }

fun <T, U> Flow<T>.zipPair(other : Flow<U>) : Flow<Pair<T, U>> =
    zip(other) { t, u -> t to u }

fun <T, U> Flow<T>.zipPair(other : U) : Flow<Pair<T, U>> =
    zip(flowOf(other)) { t, u -> t to u }