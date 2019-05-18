package com.worldsnas.moviedetail

import com.worldsnas.base.BasePresenter
import com.worldsnas.base.BaseState
import com.worldsnas.mvi.MviProcessor
import javax.inject.Inject

class MovieDetailPresenter @Inject constructor(
        processor: MviProcessor<MovieDetailIntent, MovieDetailResult>
) : BasePresenter<MovieDetailIntent, MovieDetailState, MovieDetailResult>(
        processor,
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
                            base = BaseState.stable(),
                            title = result.title,
                            poster = result.poster,
                            duration = result.duration,
                            date = result.date,
                            description = result.description
                    )
            }
}