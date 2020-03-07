package com.worldsnas.home.view

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.slider.library.slider
import com.jakewharton.rxbinding3.view.clicks
import com.worldsnas.androidcore.helpers.pages
import com.worldsnas.androidcore.transitionNameCompat
import com.worldsnas.base.CoroutineView
import com.worldsnas.core.asFlow
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.coreComponent
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomeState
import com.worldsnas.home.adapter.HomeAdapter
import com.worldsnas.home.databinding.ViewHomeBinding
import com.worldsnas.home.di.DaggerHomeComponent
import com.worldsnas.view_component.movieView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeView : CoroutineView<ViewHomeBinding, HomeState, HomeIntent> {

    constructor() : super()

    constructor(app: Application) : this(core = app.coreComponent())

    constructor(core: CoreComponent) : super() {
        coreComponent = core
    }

    @Inject
    lateinit var homeAdapter: HomeAdapter

    override fun injectDependencies(core: CoreComponent) {
        DaggerHomeComponent
            .builder()
            .bindRouter(router)
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): ViewHomeBinding = ViewHomeBinding.inflate(inflater, container, false)

    override fun onViewBound(binding: ViewHomeBinding) {
        initRv(binding)
        binding.ablHome.outlineProvider = null
        binding.txtSearchName.transitionNameCompat = "search_name"
        binding.toolbarHome.transitionNameCompat = "search_back"
        super.onViewBound(binding)
    }

    override fun unBindView() {
        super.unBindView()
    }

    private fun initRv(binding: ViewHomeBinding) {
//        binding.rvHome.layoutManager =
//            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        binding.rvHome.layoutManager = GridLayoutManager(binding.root.context, 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

                override fun getSpanSize(position: Int): Int =
                    if (position == 0) {
                        3
                    } else {
                        1
                    }
            }
        }
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)

        binding.rvHome.withModelsAsync {
            slider {
                id("home-slider")
                models(
                    state.sliderMovies.map {movie->
                        BannerViewModel_().apply {
                            id(movie.id)
                            movie(movie)
                        }
                    }
                )
            }


            state.latest.forEach {
                movieView {
                    id(it.id)
                    movie(it)
                    listener { listenMovie ->
                        presenter.processIntents(
                            HomeIntent.LatestMovieClicked(
                                listenMovie
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
