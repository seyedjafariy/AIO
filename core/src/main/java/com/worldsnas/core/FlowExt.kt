package com.worldsnas.core

import kotlinx.coroutines.flow.*

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





