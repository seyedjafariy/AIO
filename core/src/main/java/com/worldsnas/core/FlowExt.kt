package com.worldsnas.core

import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicReference

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

public fun <T: Any> ObservableSource<T>.asFlow(): Flow<T> = callbackFlow {
    val disposableRef = AtomicReference<Disposable>()
    val observer = object : Observer<T> {
        override fun onComplete() { close() }
        override fun onSubscribe(d: Disposable) { if (!disposableRef.compareAndSet(null, d)) d.dispose() }
        override fun onNext(t: T) { sendBlocking(t) }
        override fun onError(e: Throwable) { close(e) }
    }

    subscribe(observer)
    awaitClose { disposableRef.getAndSet(Disposed)?.dispose() }
}

private object Disposed : Disposable {
    override fun isDisposed() = true
    override fun dispose() = Unit
}

