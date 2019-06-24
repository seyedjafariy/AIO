package com.worldsnas.home

import com.worldsnas.base.BaseState
import com.worldsnas.core.notOfType
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.home.model.HomeUIModel
import com.worldsnas.home.model.MovieUIModel
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
        private val processor: MviProcessor<HomeIntent, HomeResult>
) : MviPresenter<HomeIntent, HomeState> {

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
            is HomeResult.Error ->
                preState.copy(base = BaseState.withError(result.err))
            is HomeResult.LatestMovies ->
                combineLatest(preState, result.movies.map { movie-> HomeUIModel.LatestMovie(movie) })
//                preState.copy(base = BaseState.stable(), latest = result.movies)
            is HomeResult.TrendingMovies ->
                combineTrends(preState, result.movies)
//                preState.copy(base = BaseState.stable(), sliderMovies = result.movies)
            HomeResult.Loading ->
                preState.copy(base = BaseState.loading())
            HomeResult.LastStable ->
                preState.copy(base = BaseState.stable())
        }
    }

    private fun combineTrends(preState: HomeState, slides: List<MovieUIModel>): HomeState =
            if (preState.homeItems.isNotEmpty()) {
                if (preState.homeItems.first() is HomeUIModel.Slider) {
                    preState.copy(
                            base = BaseState.stable(),
                            homeItems = mutableListOf<HomeUIModel>(
                                    HomeUIModel.Slider(
                                            slides
                                    )
                            ).apply {
                                val oldList = preState.homeItems.toMutableList()
                                oldList.removeAt(0)
                                addAll(oldList)
                            }
                    )
                } else {
                    preState.copy(
                            base = BaseState.stable(),
                            homeItems = mutableListOf<HomeUIModel>(
                                    HomeUIModel.Slider(
                                            slides
                                    )
                            ).apply {
                                addAll(preState.homeItems)
                            }
                    )
                }
            } else {
                preState.copy(
                        base = BaseState.stable(),
                        homeItems = listOf<HomeUIModel>(
                                HomeUIModel.Slider(
                                        slides
                                )
                        )
                )
            }

    private fun combineLatest(preState: HomeState, latest: List<HomeUIModel>) =
            if (preState.homeItems.isNotEmpty()) {
                if (preState.homeItems.first() is HomeUIModel.Slider) {
                    preState.copy(
                            base = BaseState.stable(),
                            homeItems = mutableListOf<HomeUIModel>(
                                    preState.homeItems.first()
                            ).apply {
                                addAll(latest)
                            }
                    )
                } else {
                    preState.copy(
                            base = BaseState.stable(),
                            homeItems = latest
                    )
                }
            } else {
                preState.copy(
                        base = BaseState.stable(),
                        homeItems = latest
                )
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