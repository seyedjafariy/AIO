package com.worldsnas.moviedetail

import com.worldsnas.mvi.MviPresenter
import io.reactivex.Observable
import javax.inject.Inject

class MovieDetailPresenter @Inject constructor(
): MviPresenter<MovieDetailIntent, MovieDetailState>{

    override fun processIntents(intents: MovieDetailIntent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun states(): Observable<MovieDetailState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}