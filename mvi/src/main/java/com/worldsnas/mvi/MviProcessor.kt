package com.worldsnas.mvi

import com.worldsnas.mvi.MviAction
import io.reactivex.ObservableTransformer

interface MviProcessor<A : MviAction, R : MviResult> {

    val actionProcessor: ObservableTransformer<A, R>
}