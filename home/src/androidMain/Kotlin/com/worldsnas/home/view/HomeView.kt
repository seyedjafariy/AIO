package com.worldsnas.home.view

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import com.worldsnas.base.CoroutineView
import com.worldsnas.base.pages
import com.worldsnas.base.transitionNameCompat
import com.worldsnas.core.mvi.MviPresenter
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.coreComponent
import com.worldsnas.daggercore.lifecycleComponent
import com.worldsnas.daggercore.navigator.DaggerDefaultNavigationComponent
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomeState
import com.worldsnas.home.databinding.ViewHomeBinding
import com.worldsnas.home.di.DaggerHomeComponent
import com.worldsnas.home.model.Movie
import com.worldsnas.slider.slider
import com.worldsnas.view_component.movieView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.rx2.asFlow
import javax.inject.Inject

class HomeView : CoroutineView<ViewHomeBinding, HomeState, HomeIntent> {

    @Inject
    override lateinit var presenter: MviPresenter<HomeIntent, HomeState>

    constructor() : super()

    constructor(app: Application) : this(core = app.coreComponent())

    constructor(core: CoreComponent) : super() {
        coreComponent = core
    }

    override fun injectDependencies(core: CoreComponent) {
        DaggerHomeComponent
            .builder()
            .coreComponent(core)
            .lifecycleComponent(activity!!.lifecycleComponent)
            .navComponent(DaggerDefaultNavigationComponent.factory().create(core, router))
            .build()
            .inject(this)
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): ViewHomeBinding = ViewHomeBinding.inflate(inflater, container, false)

    override fun beforeBindingView(binding: ViewHomeBinding) {
        initRv(binding)
        binding.ablHome.outlineProvider = null
        binding.txtSearchName.transitionNameCompat = "search_name"
        binding.toolbarHome.transitionNameCompat = "search_back"
    }

    private fun initRv(binding: ViewHomeBinding) {
        binding.rvHome.layoutManager = GridLayoutManager(binding.root.context, 3)
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)

        binding.rvHome.withModelsAsync {
            slider {
                spanSizeOverride { _, _, _ ->
                    3
                }
                id("home-slider")
                infinite(true)
                cycleDelay(3000)
                indicatorVisible(false)
                copier { oldModel ->
                    oldModel as BannerViewModel_
                    BannerViewModel_().apply {
                        id(oldModel.movie().id)
                        movie(oldModel.movie())
                        listener { listenMovie ->
                            presenter.processIntents(
                                HomeIntent.LatestMovieClicked(
                                    listenMovie
                                )
                            )
                        }
                    }
                }
                models(
                    state.sliderMovies.map { movie ->
                        BannerViewModel_().apply {
                            id(movie.id)
                            movie(movie)
                            listener { listenMovie ->
                                presenter.processIntents(
                                    HomeIntent.LatestMovieClicked(
                                        listenMovie
                                    )
                                )
                            }
                        }
                    }
                )
            }


            state.latest.forEach {
                movieView {
                    id(it.id)
                    spanSizeOverride { totalSpanCount, position, itemCount ->
                        1
                    }
                    title(it.title)
                    movieId(it.id)
                    releaseDate(it.releaseDate)
                    poster(it.poster)
                    listener { movieId, movieTitle, poster, releaseDate ->
                        presenter.processIntents(
                            HomeIntent.LatestMovieClicked(
                                Movie(
                                    id = movieId,
                                    poster = poster,
                                    cover = "",
                                    title = movieTitle,
                                    releaseDate = releaseDate
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    override fun intents(): Flow<HomeIntent> =
        Observable.merge(
            Observable.just(HomeIntent.Initial),
            binding.rvHome.pages()
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    HomeIntent.NextPage(it.page, it.totalItemsCount)
                },
            binding.imgSearch.clicks()
                .map {
                    HomeIntent.SearchClicks(
                        binding.toolbarHome.transitionNameCompat ?: "",
                        binding.txtSearchName.transitionNameCompat ?: ""
                    )
                }
        ).asFlow()
}