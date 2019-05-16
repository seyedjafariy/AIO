package com.worldsnas.moviedetail

import com.worldsnas.base.BasePresenter
import com.worldsnas.base.BaseState
import javax.inject.Inject

class MovieDetailPresenter @Inject constructor(
) : BasePresenter<MovieDetailIntent, MovieDetailState, MovieDetailResult>(
    MovieDetailState.start()
) {

    override fun reduce(preState: MovieDetailState, result: MovieDetailResult): MovieDetailState =
        when (result) {
            MovieDetailResult.LastStable ->
                preState.copy(
                    base = BaseState.stable()
                )
            is MovieDetailResult.Error ->
                preState.copy(
                    base = BaseState.withError(result.err)
                )
            is MovieDetailResult.Detail ->
                preState.copy(
                    movieTitle = result.movieTitle
                )
        }
}