package com.worldsnas.core.mvi

import com.worldsnas.core.FlowBlock
import com.worldsnas.core.publish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface MviProcessor<A : MviIntent, R : MviResult> {

    val actionProcessor: (Flow<A>) -> Flow<R>
}

abstract class BaseProcessor<A : MviIntent, R : MviResult> : MviProcessor<A, R> {
    final override val actionProcessor: (Flow<A>) -> Flow<R> = {
            it.publish(transformers())
    }

    protected abstract fun transformers(): List<FlowBlock<A, R>>
}