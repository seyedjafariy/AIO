package com.worldsnas.core.mvi

import kotlinx.coroutines.flow.Flow

/**
 * Object representing a UI that will
 * a) emit its intents to a view model,
 * b) subscribes to a view model for rendering its UI.
 *
 * @param I Top class of the [MviIntent] that the [MviView] will be emitting.
 * @param S Top class of the [MviViewState] the [MviView] will be subscribing to.
 */
interface MviView<I : MviIntent, in S : MviViewState> {

    fun intents(): Flow<I>

    fun render(state: S)

}
