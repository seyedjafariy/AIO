package com.worldsnas.home

import com.worldsnas.base.BaseState
import com.worldsnas.core.notOfType
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.mvi.MviProcessor
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

@FeatureScope
class HomePresenter @Inject constructor(
    private val processor : MviProcessor<HomeIntent, HomeResult>
): MviPresenter<HomeIntent, HomeState> {

    private val intentFilter = ObservableTransformer<HomeIntent, HomeIntent> { intents ->
        intents.publish { share ->
            Observable.merge(
                share.ofType(HomeIntent.Initial::class.java).take(1),
                share.notOfType(HomeIntent.Initial::class.java)
            )
        }
    }

    private val reducer = BiFunction<HomeState, HomeResult, HomeState> { preState, result ->
        when (result) {
            is HomeResult.Error->
                preState.copy(base = BaseState.withError(result.err))
            is HomeResult.LatestMovies ->
                preState.copy(base= BaseState.stable(), latest = result.movies)
            is HomeResult.TrendingMovies ->
                preState.copy(base= BaseState.stable(), sliderMovies = result.movies)
            HomeResult.Loading ->
                preState.copy(base = BaseState.loading())
            HomeResult.LastStable ->
                preState.copy(base = BaseState.stable())
        }
    }

    private val intentsSubject: PublishSubject<HomeIntent> = PublishSubject.create()
    private val statesObservable: Observable<HomeState> = compose()

    private fun compose(): Observable<HomeState> =
        intentsSubject
            .compose(intentFilter)
            .compose(processor.actionProcessor)
            .scan(HomeState.start(), reducer)
            .doOnNext { Timber.d("new state created= $it") }
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)

    override fun processIntents(intents: HomeIntent) {
        intentsSubject.onNext(intents)
    }

    override fun states(): Observable<HomeState> = statesObservable

}