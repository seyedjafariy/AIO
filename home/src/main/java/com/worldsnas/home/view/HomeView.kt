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
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomeState
import com.worldsnas.home.R
import com.worldsnas.home.R2
import com.worldsnas.home.adapter.HomeMoviesAdapter
import com.worldsnas.home.di.DaggerHomeComponent
import com.worldsnas.home.model.MovieUIModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Provider

class HomeView : BaseView<HomeState, HomeIntent>(),
    BaseSliderView.OnSliderClickListener {

    @BindView(R2.id.movies)
    lateinit var movies : RecyclerView
    @BindView(R2.id.slider)
    lateinit var slider : SliderLayout

    @Inject
    lateinit var movieAdapterProvider: Provider<HomeMoviesAdapter>
    lateinit var movieAdapter: HomeMoviesAdapter

    var sliderItems: List<MovieUIModel> = emptyList()

    override fun getLayoutId(): Int = R.layout.view_home

    override fun injectDependencies(core: CoreComponent) {
        DaggerHomeComponent.builder().coreComponent(core).build().inject(this)
    }

    override fun onAttach(view: View) {
        initRv(view)
        super.onAttach(view)
    }

    override fun onDetach(view: View) {
        sliderItems = emptyList()
        super.onDetach(view)
    }

    private fun initRv(view: View) {
        movieAdapter = movieAdapterProvider.get()
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
        Observable.just(HomeIntent.Initial)

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