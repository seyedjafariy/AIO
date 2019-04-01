package com.worldsnas.basemvi

import io.reactivex.ObservableTransformer

interface MviProcessor<A : MviAction, R : MviResult> {

    val actionProcessor: ObservableTransformer<A, R>
}