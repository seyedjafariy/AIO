package com.worldsnas.home

import com.worldsnas.base.BaseView
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.home.di.DaggerHomeComponent
import io.reactivex.Observable

class HomeView : BaseView<HomeState, HomeIntent>() {

    override fun getLayoutId(): Int = R.layout.view_home

    override fun injectDependencies(core: CoreComponent) {
        DaggerHomeComponent.builder().coreComponent(core).build().inject(this)
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)
        // router.pushController(RouterTransaction.with())
    }

    override fun intents(): Observable<HomeIntent> =
        Observable.just(HomeIntent.Initial)
}