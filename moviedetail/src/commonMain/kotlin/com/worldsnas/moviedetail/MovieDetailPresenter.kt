package com.worldsnas.moviedetail

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.core.listMerge
import com.worldsnas.core.mvi.BasePresenter
import com.worldsnas.core.mvi.MviProcessor
import com.worldsnas.core.noOfType
import com.worldsnas.core.ofType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

class MovieDetailPresenter(
    processor: MviProcessor<MovieDetailIntent, MovieDetailResult>
) : BasePresenter<MovieDetailIntent, MovieDetailState, MovieDetailResult>(
    processor,
    MovieDetailState.start()
) {

    override fun filterIntent(intents: Flow<MovieDetailIntent>): Flow<MovieDetailIntent> {
        return intents.listMerge(
            {
                ofType<MovieDetailIntent.Initial>().take(1)
            },
            {
                noOfType(MovieDetailIntent.Initial::class)
            }
        )
    }

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
                    description = result.description,
                    covers = result.covers,
                    genres = result.genres,
                    recommendations = result.recommendations,
                    showRecommendation = result.recommendations.isNotEmpty(),
                    similars = result.similars,
                    showSimilar = result.similars.isNotEmpty()
                )
        }
}