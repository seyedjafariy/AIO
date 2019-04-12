package com.worldsnas.home

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.worldsnas.base.BaseView
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.home.adapter.HomeMoviesAdapter
import com.worldsnas.home.di.DaggerHomeComponent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_home.*

class HomeView : BaseView<HomeState, HomeIntent>() {

    override fun getLayoutId(): Int = R.layout.view_home

    override fun injectDependencies(core: CoreComponent) {
        DaggerHomeComponent.builder().coreComponent(core).build().inject(this)
    }

    override fun onAttach(view: View) {
        movies.layoutManager = GridLayoutManager(view.context, 3)
        movies.adapter = HomeMoviesAdapter()
        super.onAttach(view)
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)
    }

    override fun intents(): Observable<HomeIntent> =
        Observable.just(HomeIntent.Initial)
}