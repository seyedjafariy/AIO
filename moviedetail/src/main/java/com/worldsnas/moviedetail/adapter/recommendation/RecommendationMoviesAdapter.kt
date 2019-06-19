package com.worldsnas.moviedetail.adapter.recommendation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worldsnas.core.inflate
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.MovieDetailState
import com.worldsnas.moviedetail.R
import com.worldsnas.moviedetail.model.MovieUIModel
import com.worldsnas.moviedetail.view.RecommendationMovieViewHolder
import com.worldsnas.mvi.MviPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@FeatureScope
class RecommendationMoviesAdapter @Inject constructor(
    diffCallback: RecommendationMovieUIDiffCallback,
    private val presenter: MviPresenter<MovieDetailIntent, MovieDetailState>
) : ListAdapter<MovieUIModel, RecommendationMovieViewHolder>(diffCallback) {

    private val disposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationMovieViewHolder =
        RecommendationMovieViewHolder(parent.inflate(R.layout.row_recommendation_movie))

    override fun onBindViewHolder(holder: RecommendationMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.intents(getItem(position))
            .subscribeBy {
                presenter.processIntents(it)
            }
            .addTo(disposable)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        presenter
            .states()
            .subscribeBy {
                submitList(it.recommendations)
            }
            .addTo(disposable)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposable.clear()
    }
}