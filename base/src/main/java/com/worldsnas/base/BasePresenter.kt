package com.worldsnas.base

import com.worldsnas.mvi.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

abstract class BasePresenter<I : MviIntent, S : MviViewState, R : MviResult>(
    private val startState : S
) : MviPresenter<I, S> {

    @Inject
    protected lateinit var processor: MviProcessor<I, R>

    private val intentFilter = ObservableTransformer<I, I> { intents ->
        filterIntent(intents)
    }

    private val reducer = BiFunction<S, R, S> { preState, result ->
        reduce(preState, result)
    }

    private val intentsSubject: PublishSubject<I> = PublishSubject.create()
    private val statesObservable: Observable<S> = compose()

    private fun compose(): Observable<S> =
        intentsSubject
            .compose(intentFilter)
            .compose(processor.actionProcessor)
            .scan(startState, reducer)
            .doOnNext { Timber.d("new state created= $it") }
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)

    override fun processIntents(intents: I) {
        intentsSubject.onNext(intents)
    }

    override fun states(): Observable<S> = statesObservable

    protected open fun filterIntent(intents : Observable<I>) : Observable<I> =
            intents

    protected abstract fun reduce(preState : S, result : R) : S
}