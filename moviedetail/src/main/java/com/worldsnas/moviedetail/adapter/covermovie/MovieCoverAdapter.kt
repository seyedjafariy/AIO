package com.worldsnas.moviedetail.adapter.covermovie

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worldsnas.androidcore.inflate
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.MovieDetailState
import com.worldsnas.moviedetail.R
import com.worldsnas.moviedetail.model.MovieUIModel
import com.worldsnas.moviedetail.view.MovieCoverViewHolder
import com.worldsnas.mvi.MviPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MovieCoverAdapter @Inject constructor(
    diffCallback: MovieCoverUIDiffCallback,
    private val presenter: MviPresenter<MovieDetailIntent, MovieDetailState>
) : ListAdapter<MovieUIModel, MovieCoverViewHolder>(diffCallback) {

    private val disposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCoverViewHolder =
        MovieCoverViewHolder(parent.inflate(R.layout.row_recommendation_movie))

    override fun onBindViewHolder(holder: MovieCoverViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.intents(getItem(position))
            .subscribeBy {
                presenter.processIntents(it)
            }
            .addTo(disposable)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposable.clear()
    }
}