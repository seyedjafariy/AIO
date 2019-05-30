package com.worldsnas.moviedetail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worldsnas.core.inflate
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.MovieDetailState
import com.worldsnas.moviedetail.R
import com.worldsnas.moviedetail.model.GenreUIModel
import com.worldsnas.moviedetail.view.GenreViewHolder
import com.worldsnas.mvi.MviPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GenreAdapter @Inject constructor(
    diffCallback: GenreDiffCallback,
    private val presenter: MviPresenter<MovieDetailIntent, MovieDetailState>
) : ListAdapter<GenreUIModel, GenreViewHolder>(diffCallback) {

    private val disposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(parent.inflate(R.layout.row_genre))

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.intents(getItem(position))
            .subscribeBy {
                presenter.processIntents(it)
            }
            .addTo(disposable)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        presenter.states()
            .subscribeBy {
                submitList(it.genres)
            }.addTo(disposable)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposable.clear()
    }
}