package com.worldsnas.home

import com.worldsnas.core.listMerge
import com.worldsnas.core.mvi.BasePresenter
import com.worldsnas.core.mvi.BaseState
import com.worldsnas.core.mvi.MviProcessor
import com.worldsnas.core.noOfType
import com.worldsnas.core.ofType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

class HomePresenter(
    processor: MviProcessor<HomeIntent, HomeResult>
) : BasePresenter<HomeIntent, HomeState, HomeResult>(processor, HomeState.start()) {

    override fun filterIntent(intents: Flow<HomeIntent>): Flow<HomeIntent> = intents.listMerge(
        {
            ofType<HomeIntent.Initial>().take(1)
        },
        {
            noOfType(HomeIntent.Initial::class)
        }
    )

    override fun reduce(preState: HomeState, result: HomeResult): HomeState = when (result) {
        is HomeResult.Error ->
            preState.copy(base = BaseState.withError(result.err))
        is HomeResult.LatestMovies ->
            preState.copy(base = BaseState.stable(), latest = result.movies)
        is HomeResult.TrendingMovies ->
            preState.copy(base = BaseState.stable(), sliderMovies = result.movies)
        HomeResult.Loading ->
            preState.copy(base = BaseState.loading())
        HomeResult.LastStable ->
            preState.copy(base = BaseState.stable())
    }
}