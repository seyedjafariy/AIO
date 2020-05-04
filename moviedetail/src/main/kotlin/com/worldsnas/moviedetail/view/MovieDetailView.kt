package com.worldsnas.moviedetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.worldsnas.base.*
import com.worldsnas.core.mvi.MviPresenter
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.domain.helpers.coverFullUrl
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.MovieDetailState
import com.worldsnas.moviedetail.adapter.GenreAdapter
import com.worldsnas.moviedetail.adapter.covermovie.MovieCoverAdapter
import com.worldsnas.moviedetail.di.DaggerMovieDetailComponent
import com.worldsnas.navigation.fromByteArray
import com.worldsnas.navigation.model.MovieDetailLocalModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class MovieDetailView(
    bundle: Bundle
) : CoroutineView<ViewMovieDetailBinding, MovieDetailState, MovieDetailIntent>(
    bundle
), BaseSliderView.OnSliderClickListener {

    @Inject
    override lateinit var presenter: MviPresenter<MovieDetailIntent, MovieDetailState>

    @Inject
    lateinit var genreAdapter: GenreAdapter

    @Inject
    lateinit var recommendationAdapter: MovieCoverAdapter

    @Inject
    lateinit var similarAdapter: MovieCoverAdapter

    private val movieLocal: MovieDetailLocalModel = bundle
        .getByteArray(MovieDetailLocalModel.EXTRA_MOVIE)
        ?.let {
            MovieDetailLocalModel.fromByteArray(it)
        }
        ?: throw NullPointerException("${MovieDetailLocalModel.EXTRA_MOVIE} can not be null")

    private var covers: List<String> = emptyList()

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewMovieDetailBinding =
        ViewMovieDetailBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) =
        DaggerMovieDetailComponent.builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)

    override fun onViewBound(binding: ViewMovieDetailBinding) {
        ViewCompat.setTransitionName(binding.imgMoviePoster, movieLocal.posterTransName)
        ViewCompat.setTransitionName(binding.txtMovieTitle, movieLocal.titleTransName)
        ViewCompat.setTransitionName(binding.txtMovieDate, movieLocal.releaseTransName)

        initGenreRV()
        initRecommendationRV()
        initSimilarRV()
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        binding.sliderMovieCover.startAutoCycle()
    }

    override fun onDetach(view: View) {
        binding.sliderMovieCover.stopAutoCycle()
        binding.rvMovieGenre.adapter = null
        super.onDetach(view)
    }

    override fun onDestroyView(view: View) {
        covers = emptyList()
        super.onDestroyView(view)
    }

    override fun render(state: MovieDetailState) {
        renderLoading(state.base)
        renderError(state.base)

        binding.txtMovieRecommendationTitle visible state.showRecommendation
        binding.rvRecommendations visible state.showRecommendation

        binding.txtMovieSimilarTitle visible state.showSimilar
        binding.rvSimilars visible state.showSimilar

        genreAdapter.submitList(state.genres)

        similarAdapter.submitList(state.similars)
        recommendationAdapter.submitList(state.recommendations)

        binding.txtMovieTitle.text = state.title
        binding.txtMovieDescription.text = state.description
        binding.txtMovieDate.text = state.date
        binding.txtMovieDuration.text = state.duration

        applicationContext?.run {
            val posterSize = 100f pixel this
            binding.imgMoviePoster.setImageURI(state.poster posterFullUrl posterSize.roundToInt())
        }
        submitCovers(state.covers)
    }

    override fun intents(): Flow<MovieDetailIntent> =
        listOf(
            flowOf(MovieDetailIntent.Initial(movieLocal)),
            posterClicks()
        ).merge()

    private fun initGenreRV() {
        binding.rvMovieGenre.adapter = genreAdapter
        binding.rvMovieGenre.layoutManager = LinearLayoutManager(
            binding.rvMovieGenre.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun initRecommendationRV() {
        binding.rvRecommendations.adapter = recommendationAdapter
        binding.rvRecommendations.layoutManager = LinearLayoutManager(
            binding.rvRecommendations.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun initSimilarRV() {
        binding.rvSimilars.adapter = similarAdapter
        binding.rvSimilars.layoutManager = LinearLayoutManager(
            binding.rvSimilars.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun submitCovers(covers: List<String>) {
        if (this.covers != covers) {
            view?.context?.run {
                binding.sliderMovieCover.removeAllSliders()
                covers.forEach {
                    binding.sliderMovieCover.addSlider(
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
                    binding.sliderMovieCover.setPresetTransformer(SliderLayout.Transformer.Default)
                    this@MovieDetailView.covers = covers
                }
            }
        }
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        presenter
            .processIntents(
                MovieDetailIntent.CoverClicked(
                    slider?.bundle?.getString("url") ?: "",
                    binding.sliderMovieCover.parent as ConstraintLayout getCenterX binding.sliderMovieCover.parent as ConstraintLayout,
                    binding.sliderMovieCover.parent as ConstraintLayout getCenterY binding.sliderMovieCover.parent as ConstraintLayout
                )
            )
    }

    private fun posterClicks() = callbackFlow {
        binding.imgMoviePoster.setOnClickListener {
            launch {
                this@callbackFlow.send(
                    MovieDetailIntent.PosterClicked(
                        binding.imgMoviePoster getCenterX binding.imgMoviePoster.parent as ConstraintLayout,
                        binding.imgMoviePoster getCenterY binding.imgMoviePoster.parent as ConstraintLayout
                    )
                )
            }
        }
        awaitClose {
            binding.imgMoviePoster.setOnClickListener(null)
        }
    }
}