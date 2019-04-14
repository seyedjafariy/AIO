package com.worldsnas.mvi

import io.reactivex.ObservableTransformer

interface MviProcessor<A : MviIntent, R : MviResult> {

    val actionProcessor: ObservableTransformer<A, R>
}