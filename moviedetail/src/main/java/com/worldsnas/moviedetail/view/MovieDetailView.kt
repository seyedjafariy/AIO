package com.worldsnas.moviedetail.view

import android.os.Bundle
import com.worldsnas.base.BaseView
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.MovieDetailState
import com.worldsnas.moviedetail.R
import com.worldsnas.moviedetail.di.DaggerMovieDetailComponent
import com.worldsnas.navigation.model.MovieDetailLocalModel
import io.reactivex.Observable

class MovieDetailView(
    bundle: Bundle
) : BaseView<MovieDetailState, MovieDetailIntent>(bundle) {

    private val movieLocal: MovieDetailLocalModel = bundle
        .getParcelable(MovieDetailLocalModel.EXTRA_MOVIE)
        ?: throw NullPointerException("${MovieDetailLocalModel.EXTRA_MOVIE} can not be null")

    override fun getLayoutId(): Int = R.layout.view_movie_detail

    override fun injectDependencies(core: CoreComponent) =
        DaggerMovieDetailComponent.builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)

    override fun render(state: MovieDetailState) {
        renderLoading(state.base)
        renderError(state.base)

    }

    override fun intents(): Observable<MovieDetailIntent> =
        Observable.just(MovieDetailIntent.Initial(movieLocal.movieID))
}