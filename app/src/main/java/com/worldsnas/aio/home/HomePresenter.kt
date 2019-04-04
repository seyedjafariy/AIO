package com.worldsnas.aio.home

import com.worldsnas.mvi.MviPresenter
import io.reactivex.Observable
import timber.log.Timber

class HomePresenter : MviPresenter<HomeIntent, HomeState> {
    override fun processIntents(intents: Observable<HomeIntent>) {
        Timber.d("")
    }

    override fun states(): Observable<HomeState> {
        return Observable.just(HomeState.startingState())
    }
}