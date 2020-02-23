package com.worldsnas.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

fun <T, U> Flow<T>.listMerge(block: (Flow<T>) -> List<Flow<U>>) =
    block(this)
        .merge()

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



