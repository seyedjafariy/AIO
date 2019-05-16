package com.worldsnas.moviedetail

import com.worldsnas.base.BasePresenter
import javax.inject.Inject

class MovieDetailPresenter @Inject constructor(
) : BasePresenter<MovieDetailIntent, MovieDetailState, MovieDetailResult>(
    MovieDetailState.start()
) {

    override fun reduce(preState: MovieDetailState, result: MovieDetailResult): MovieDetailState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}