package com.worldsnas.moviedetail.view

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.daimajia.slider.library.SliderLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.worldsnas.base.BaseView
import com.worldsnas.core.pixel
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.MovieDetailState
import com.worldsnas.moviedetail.R
import com.worldsnas.moviedetail.R2
import com.worldsnas.moviedetail.di.DaggerMovieDetailComponent
import com.worldsnas.navigation.model.MovieDetailLocalModel
import io.reactivex.Observable
import kotlin.math.roundToInt

class MovieDetailView(
        bundle: Bundle
) : BaseView<MovieDetailState, MovieDetailIntent>(bundle) {

    @BindView(R2.id.sliderMovieCover)
    lateinit var coverSlider: SliderLayout
    @BindView(R2.id.imgMoviePoster)
    lateinit var poster: SimpleDraweeView
    @BindView(R2.id.txtMovieTitle)
    lateinit var title: TextView
    @BindView(R2.id.txtMovieDuration)
    lateinit var duration: TextView
    @BindView(R2.id.txtMovieDate)
    lateinit var date: TextView
    @BindView(R2.id.rvMovieGenre)
    lateinit var genres: RecyclerView
    @BindView(R2.id.txtMovieDescription)
    lateinit var description: TextView

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

        title.text = state.title
        description.text = state.description
        date.text = state.date
        duration.text = state.duration

        applicationContext?.run {
            val posterSize = 100f pixel this
            poster.setImageURI(state.poster posterFullUrl posterSize.roundToInt())
        }
    }

    override fun intents(): Observable<MovieDetailIntent> =
            Observable.just(MovieDetailIntent.Initial(movieLocal))
}