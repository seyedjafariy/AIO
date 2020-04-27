package com.worldsnas.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worldsnas.base.inflate
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomeState
import com.worldsnas.home.R
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.home.view.HomeMovieViewHolder
import com.worldsnas.mvi.MviPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeMoviesAdapter @Inject constructor(
    diffCallback: MovieUIDiffCallback,
    private val presenter: MviPresenter<HomeIntent, HomeState>
) : ListAdapter<MovieUIModel, HomeMovieViewHolder>(diffCallback) {

    private val disposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieViewHolder =
        HomeMovieViewHolder(parent.inflate(R.layout.row_home_movie))

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
//        holder.intents(getItem(position))
//            .subscribeBy {
//                presenter.processIntents(it)
//            }
//            .addTo(disposable)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposable.clear()
    }
}