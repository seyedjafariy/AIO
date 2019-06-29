package com.worldsnas.search.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.worldsnas.base.BaseView
import com.worldsnas.core.showKeyboard
import com.worldsnas.core.transitionNameCompat
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.navigation.model.SearchLocalModel
import com.worldsnas.search.R
import com.worldsnas.search.SearchIntent
import com.worldsnas.search.SearchState
import com.worldsnas.search.di.DaggerSearchComponent
import io.reactivex.Observable

class SearchView(
    bundle: Bundle
) : BaseView<SearchState, SearchIntent>(bundle) {

    lateinit var searchBack: View
    lateinit var searchEditText: EditText
    lateinit var backBtn: ImageView

    private val searchLocal = bundle.getParcelable<SearchLocalModel>(SearchLocalModel.EXTRA_SEARCH)

    override fun getLayoutId(): Int = R.layout.view_search

    override fun injectDependencies(core: CoreComponent) {
        DaggerSearchComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        searchBack = view.findViewById(R.id.vSearchBack)
        searchEditText = view.findViewById(R.id.edtSearch)
        backBtn = view.findViewById(R.id.imgBack)

        searchLocal?.run {
            searchBack.transitionNameCompat = backTransitionName
            searchEditText.transitionNameCompat = textTransitionName
        }

        backBtn.setOnClickListener {
            router.handleBack()
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        searchEditText.requestFocus()
        showKeyboard()
    }

    override fun render(state: SearchState) {
        renderLoading(state.base)
        renderError(state.base)
    }

    override fun intents(): Observable<SearchIntent> =
        Observable.just(SearchIntent.Initial)
}