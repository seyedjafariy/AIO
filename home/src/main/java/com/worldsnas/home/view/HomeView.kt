package com.worldsnas.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import com.worldsnas.androidcore.helpers.pages
import com.worldsnas.androidcore.transitionNameCompat
import com.worldsnas.base.CoroutineView
import com.worldsnas.core.asFlow
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomeState
import com.worldsnas.home.adapter.HomeAdapter
import com.worldsnas.home.databinding.ViewHomeBinding
import com.worldsnas.home.di.DaggerHomeComponent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeView : CoroutineView<ViewHomeBinding, HomeState, HomeIntent>() {

    @Inject
    lateinit var homeAdapter: HomeAdapter

    override fun injectDependencies(core: CoreComponent) {
        DaggerHomeComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): ViewHomeBinding = ViewHomeBinding.inflate(inflater, container, false)

    override fun onViewBound(binding: ViewHomeBinding) {
        super.onViewBound(binding)
        initRv(binding)
        binding.ablHome.outlineProvider = null
        binding.txtSearchName.transitionNameCompat = "search_name"
        binding.toolbarHome.transitionNameCompat = "search_back"
    }

    override fun unBindView() {
        binding.rvHome.adapter = null
        super.unBindView()
    }

    private fun initRv(binding: ViewHomeBinding) {
        binding.rvHome.layoutManager = GridLayoutManager(binding.root.context, 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    if (homeAdapter.getItemViewType(position) == 0) {
                        3
                    } else {
                        1
                    }
            }
        }
        binding.rvHome.adapter = homeAdapter
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)

        homeAdapter.submitList(state.homeItems)
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
