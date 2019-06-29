package com.worldsnas.search.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding2.widget.textChangeEvents
import com.worldsnas.base.BaseView
import com.worldsnas.base.epoxyhelper.EpoxyAsyncRecyclerView
import com.worldsnas.core.helpers.pages
import com.worldsnas.core.showKeyboard
import com.worldsnas.core.transitionNameCompat
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.navigation.model.SearchLocalModel
import com.worldsnas.search.R
import com.worldsnas.search.SearchIntent
import com.worldsnas.search.SearchState
import com.worldsnas.search.di.DaggerSearchComponent
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import java.util.concurrent.TimeUnit

class SearchView(
    bundle: Bundle
) : BaseView<SearchState, SearchIntent>(bundle) {

    lateinit var searchBack: View
    lateinit var searchEditText: EditText
    lateinit var backBtn: ImageView
    lateinit var searchList: EpoxyAsyncRecyclerView

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
        searchList = view.findViewById(R.id.searchList)

        searchLocal?.run {
            searchBack.transitionNameCompat = backTransitionName
            searchEditText.transitionNameCompat = textTransitionName
        }

        backBtn.setOnClickListener {
            router.handleBack()
        }
        searchEditText.requestFocus()
        view.showKeyboard()

        val layoutManager = GridLayoutManager(view.context, 3)
        searchList.layoutManager = layoutManager
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        searchList.adapter = null
    }

    override fun render(state: SearchState) {
        renderLoading(state.base)
        renderError(state.base)

        renderList(state)
    }

    override fun intents(): Observable<SearchIntent> =
        Observable.merge(
            searchQueryIntents(),
            nextPageIntents()
        )

    private fun searchQueryIntents() =
        searchEditText.textChangeEvents()
            .skipInitialValue()
            .filter { it.text().toString().length > 1 }
            .debounce(1, TimeUnit.SECONDS)
            .map {
                it.text().toString()
            }
            .map {
                SearchIntent.Search(it)
            }

    private fun nextPageIntents() =
        searchList.pages()
            .withLatestFrom(
                searchEditText.textChangeEvents()
                    .skipInitialValue()
                    .filter { it.text().toString().length > 1 }
            )
            .map {
                SearchIntent.NextPage(
                    it.second.text().toString(),
                    it.first.totalItemsCount,
                    it.first.page
                )
            }

    private fun renderList(state: SearchState) {
        searchList.withModelsAsync {
            state.movies.forEach {
                MovieRow(it, presenter)
                    .id(it.id)
                    .addTo(this)
            }
        }
    }
}