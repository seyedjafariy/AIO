package com.worldsnas.home

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.mvi.MviProcessor
import io.reactivex.ObservableTransformer
import javax.inject.Inject

@FeatureScope
class HomeProcessor @Inject constructor(
): MviProcessor<HomeAction, HomeResult> {
    override val actionProcessor: ObservableTransformer<HomeAction, HomeResult>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}