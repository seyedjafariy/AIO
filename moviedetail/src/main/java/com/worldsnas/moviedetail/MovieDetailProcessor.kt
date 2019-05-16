package com.worldsnas.moviedetail

import com.worldsnas.mvi.MviProcessor
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MovieDetailProcessor @Inject constructor(
): MviProcessor<MovieDetailIntent, MovieDetailResult>{
    override val actionProcessor: ObservableTransformer<MovieDetailIntent, MovieDetailResult>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}