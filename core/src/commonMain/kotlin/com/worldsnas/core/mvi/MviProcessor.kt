package com.worldsnas.core.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface MviProcessor<A : MviIntent, R : MviResult> {

    val actionProcessor: (Flow<A>) -> Flow<R>
}

abstract class BaseProcessor<A : MviIntent, R : MviResult> : MviProcessor<A, R> {
    final override val actionProcessor: (Flow<A>) -> Flow<R> = {
        it
            .broadcastIn(CoroutineScope(Dispatchers.Default))
            .asFlow()
            .transformers()
            .merge()

//        it.flatMapMerge {
//            flowOf(it).transformers().merge()
//        }
    }

    protected abstract fun Flow<A>.transformers(): List<Flow<R>>
}