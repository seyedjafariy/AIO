package com.worldsnas.home

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.mvi.MviProcessor
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

@FeatureScope
class HomePresenter @Inject constructor(
    private val processor : MviProcessor<HomeAction, HomeResult>
): MviPresenter<HomeIntent, HomeState> {

    override fun processIntents(intents: Observable<HomeIntent>) {
        Timber.d("")
    }

    override fun states(): Observable<HomeState> {
        return Observable.just(HomeState.startingState())
    }
}