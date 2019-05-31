package com.worldsnas.moviedetail.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.facebook.drawee.view.SimpleDraweeView
import com.jakewharton.rxbinding2.view.clicks
import com.worldsnas.base.BaseView
import com.worldsnas.core.getScreenWidth
import com.worldsnas.core.pixel
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.domain.helpers.coverFullUrl
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.MovieDetailState
import com.worldsnas.moviedetail.R
import com.worldsnas.moviedetail.R2
import com.worldsnas.moviedetail.adapter.GenreAdapter
import com.worldsnas.moviedetail.di.DaggerMovieDetailComponent
import com.worldsnas.navigation.model.MovieDetailLocalModel
import io.reactivex.Observable
import javax.inject.Inject
import kotlin.math.roundToInt

class MovieDetailView(
    bundle: Bundle
) : BaseView<MovieDetailState, MovieDetailIntent>(bundle), BaseSliderView.OnSliderClickListener {

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

    @Inject
    lateinit var genreAdapter: GenreAdapter

    private val movieLocal: MovieDetailLocalModel = bundle
        .getParcelable(MovieDetailLocalModel.EXTRA_MOVIE)
        ?: throw NullPointerException("${MovieDetailLocalModel.EXTRA_MOVIE} can not be null")

    private var covers: List<String> = emptyList()

    override fun getLayoutId(): Int = R.layout.view_movie_detail

    override fun injectDependencies(core: CoreComponent) =
        DaggerMovieDetailComponent.builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)

    override fun onViewBound(view: View) {
        ViewCompat.setTransitionName(poster, movieLocal.posterTransName)
        ViewCompat.setTransitionName(title, movieLocal.titleTransName)
        ViewCompat.setTransitionName(date, movieLocal.releaseTransName)

        initGenreRV()
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        coverSlider.startAutoCycle()
    }

    override fun onDetach(view: View) {
        coverSlider.stopAutoCycle()
        genres.adapter = null
        super.onDetach(view)
    }

    override fun onDestroyView(view: View) {
        covers = emptyList()
        super.onDestroyView(view)
    }

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
        submitCovers(state.covers)
    }

    override fun intents(): Observable<MovieDetailIntent> =
        Observable.merge(
            Observable.just(MovieDetailIntent.Initial(movieLocal)),
            posterClicks()
        )

    private fun initGenreRV() {
        genres.adapter = genreAdapter
        genres.layoutManager = LinearLayoutManager(
            genres.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun submitCovers(covers: List<String>) {
        if (this.covers != covers) {
            view?.context?.run {
                coverSlider.removeAllSliders()
                covers.forEach {
                    coverSlider.addSlider(
                        TextSliderView(this)
                            .image(it.coverFullUrl(getScreenWidth()))
                            .bundle(
                                bundleOf(
                                    "url" to it
                                )
                            )
                            .setOnSliderClickListener(this@MovieDetailView)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    )
                    coverSlider.setPresetTransformer(SliderLayout.Transformer.Default)
                    this@MovieDetailView.covers = covers
                }
            }
        }
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        presenter
            .processIntents(
                MovieDetailIntent.CoverClicked(
                    slider?.bundle?.getString("url") ?: ""
                )
            )
    }

    private fun posterClicks() =
        poster.clicks()
            .map {
                MovieDetailIntent.PosterClicked
            }
}