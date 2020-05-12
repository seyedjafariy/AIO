package com.worldsnas.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.worldsnas.base.*
import com.worldsnas.core.mvi.MviPresenter
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.navigation.fromByteArray
import com.worldsnas.navigation.model.SearchLocalModel
import com.worldsnas.search.SearchIntent
import com.worldsnas.search.SearchState
import com.worldsnas.search.databinding.ViewSearchBinding
import com.worldsnas.search.di.DaggerSearchComponent
import com.worldsnas.search.model.MovieUIModel
import com.worldsnas.view_component.movieView
import io.reactivex.rxkotlin.withLatestFrom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.rx2.asFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchView(
    bundle: Bundle
) : CoroutineView<ViewSearchBinding, SearchState, SearchIntent>(bundle) {

    private val searchLocal: SearchLocalModel? =
        bundle
            .getByteArray(SearchLocalModel.EXTRA_SEARCH)
            ?.let {
                SearchLocalModel.fromByteArray(it)
            }

    @Inject
    override lateinit var presenter: MviPresenter<SearchIntent, SearchState>

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewSearchBinding =
        ViewSearchBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerSearchComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)
    }

    override fun beforeBindingView(binding: ViewSearchBinding) {
        searchLocal?.run {
            binding.vSearchBack.transitionNameCompat = backTransitionName
            binding.edtSearch.transitionNameCompat = textTransitionName
        }

        binding.imgBack.setOnClickListener {
            it.hideKeyboard()
            router.handleBack()
        }
        binding.edtSearch.requestFocus()
        binding.root.showKeyboard()

        val layoutManager = GridLayoutManager(binding.root.context, 3)
        binding.searchList.layoutManager = layoutManager
    }

    override fun onDestroyView(view: View) {
        binding.searchList.adapter = null
        super.onDestroyView(view)
    }

    override fun render(state: SearchState) {
        renderLoading(state.base)
        renderError(state.base)

        renderList(state)
    }

    override fun intents(): Flow<SearchIntent> =
        listOf(
            searchQueryIntents().asFlow(),
            nextPageIntents().asFlow()
        ).merge()

    private fun searchQueryIntents() =
        binding.edtSearch.textChangeEvents()
            .skipInitialValue()
            .filter { it.text.toString().length > 1 }
            .debounce(1, TimeUnit.SECONDS)
            .map {
                it.text.toString()
            }
            .map {
                SearchIntent.Search(it)
            }

    private fun nextPageIntents() =
        binding.searchList.pages()
            .withLatestFrom(
                binding.edtSearch.textChangeEvents()
                    .skipInitialValue()
                    .filter { it.text.toString().length > 1 }
            )
            .map {
                SearchIntent.NextPage(
                    it.second.text.toString(),
                    it.first.totalItemsCount,
                    it.first.page
                )
            }

    private fun renderList(state: SearchState) {
        binding.searchList.withModelsAsync {
            state.movies.forEach {
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
                        val movie = MovieUIModel(
                            id = movieId,
                            poster = poster,
                            cover = "",
                            title = movieTitle,
                            releaseDate = releaseDate
                        )
                        presenter.processIntents(
                            SearchIntent.SearchResultClicked(
                                movie,
                                "",
                                "",
                                ""
                            )
                        )
                    }
                }
            }
        }
    }
}