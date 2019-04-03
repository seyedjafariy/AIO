package com.worldsnas.mvi

import io.reactivex.Observable

/**
 * Object that will subscribes to a [MviView]'s [MviIntent]s,
 * process it and emit a [MviViewState] back.
 *
 * @param I Top class of the [MviIntent] that the [MviPresenter] will be subscribing
 * to.
 * @param S Top class of the [MviViewState] the [MviPresenter] will be emitting.
 */
interface MviPresenter<I : MviIntent, S : MviViewState> {
    fun processIntents(intents: Observable<I>)

    fun states(): Observable<S>
}
