package com.worldsnas.home.view

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.google.android.material.appbar.AppBarLayout
import com.worldsnas.base.BaseView
import com.worldsnas.core.helpers.pages
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomeState
import com.worldsnas.home.R
import com.worldsnas.home.R2
import com.worldsnas.home.adapter.HomeAdapter
import com.worldsnas.home.di.DaggerHomeComponent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class HomeView : BaseView<HomeState, HomeIntent>(){

    @BindView(R2.id.rvHome)
    lateinit var homeList: RecyclerView
    @Inject
    lateinit var homeAdapter: HomeAdapter
    @BindView(R2.id.ablHome)
    lateinit var appBar : AppBarLayout

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
        appBar.outlineProvider = null
    }

    override fun onDestroyView(view: View) {
        homeList.adapter = null
        super.onDestroyView(view)
    }

    private fun initRv(view: View) {
        homeList.layoutManager = GridLayoutManager(view.context, 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    if (homeAdapter.getItemViewType(position) == 0){
                        3
                    }else{
                        1
                    }
            }
        }
        homeList.adapter = homeAdapter
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)

        homeAdapter.submitList(state.homeItems)
    }

    override fun intents(): Observable<HomeIntent> =
        Observable.merge(
            Observable.just(HomeIntent.Initial),
            homeList.pages()
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    HomeIntent.NextPage(it.page, it.totalItemsCount)
                }
        )
}