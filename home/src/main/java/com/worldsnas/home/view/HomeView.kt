package com.worldsnas.home.view

import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.worldsnas.base.BaseView
import com.worldsnas.core.helpers.pages
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomeState
import com.worldsnas.home.R
import com.worldsnas.home.R2
import com.worldsnas.home.adapter.HomeMoviesAdapter
import com.worldsnas.home.di.DaggerHomeComponent
import com.worldsnas.home.model.MovieUIModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class HomeView : BaseView<HomeState, HomeIntent>(),
    BaseSliderView.OnSliderClickListener {

    @BindView(R2.id.movies)
    lateinit var movies: RecyclerView
    @BindView(R2.id.slider)
    lateinit var slider: SliderLayout

    @Inject
    lateinit var movieAdapter: HomeMoviesAdapter

    private var sliderItems: List<MovieUIModel> = emptyList()

    override fun getLayoutId(): Int = R.layout.view_home

    override fun injectDependencies(core: CoreComponent) {
        DaggerHomeComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)
    }

    override fun onViewBound(view: View) {
        initRv(view)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        slider.startAutoCycle()
    }

    override fun onDetach(view: View) {
        slider.stopAutoCycle()
        super.onDetach(view)
    }

    override fun onDestroyView(view: View) {
        movies.adapter = null
        sliderItems = emptyList()
        super.onDestroyView(view)
    }

    private fun initRv(view: View) {
        movies.layoutManager = GridLayoutManager(view.context, 3)
        movies.adapter = movieAdapter
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)


        movieAdapter.submitList(state.latest)
        submitSliderItems(state)
    }

    override fun intents(): Observable<HomeIntent> =
        Observable.merge(
            Observable.just(HomeIntent.Initial),
            movies.pages()
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    HomeIntent.NextPage(it.page, it.totalItemsCount)
                }
        )

    private fun submitSliderItems(state: HomeState) {
        if (sliderItems != state.sliderMovies) {
            view?.context?.run {
                slider.removeAllSliders()
                state.sliderMovies.forEach {
                    slider.addSlider(
                        TextSliderView(this)
                            .description(it.title)
                            .image(it.cover)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .bundle(
                                bundleOf(
                                    "id" to it.id
                                )
                            )
                            .setOnSliderClickListener(this@HomeView)
                    )
                    slider.setPresetTransformer(SliderLayout.Transformer.Default)
                    sliderItems = state.sliderMovies
                }
            }
        }
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        slider!!.bundle.getInt("id")
    }
}