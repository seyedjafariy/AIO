package com.worldsnas.aio.home

import com.worldsnas.aio.R
import com.worldsnas.base.BaseView
import com.worldsnas.core.di.CoreComponent
import io.reactivex.Observable

class HomeView : BaseView<HomeState, HomeIntent, HomePresenter>() {
    override fun getLayoutId(): Int = R.layout.view_home

    override fun injectDependencies(core: CoreComponent) {
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)
    }

    override fun intents(): Observable<HomeIntent> =
        Observable.just(HomeIntent.Initial)
}