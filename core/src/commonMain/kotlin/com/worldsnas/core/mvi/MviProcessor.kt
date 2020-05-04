package com.worldsnas.core.mvi

import kotlinx.coroutines.flow.*

interface MviProcessor<A : MviIntent, R : MviResult> {

    val actionProcessor: (Flow<A>) -> Flow<R>
}

abstract class BaseProcessor<A : MviIntent, R : MviResult> : MviProcessor<A, R> {
    override val actionProcessor: (Flow<A>) -> Flow<R> = {
        it.transformers().merge()
    }

    protected abstract fun Flow<A>.transformers() : List<Flow<R>>
}