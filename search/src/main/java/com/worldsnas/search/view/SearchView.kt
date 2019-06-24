package com.worldsnas.search.view

import com.worldsnas.base.BaseView
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.search.R
import com.worldsnas.search.SearchIntent
import com.worldsnas.search.SearchState
import com.worldsnas.search.di.DaggerSearchComponent
import io.reactivex.Observable

class SearchView : BaseView<SearchState, SearchIntent>() {
    override fun getLayoutId(): Int = R.layout.view_search

    override fun injectDependencies(core: CoreComponent) {
        DaggerSearchComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)
    }

    override fun render(state: SearchState) {
        renderLoading(state.base)
        renderError(state.base)
    }

    override fun intents(): Observable<SearchIntent> =
        Observable.just(SearchIntent.Initial)
}