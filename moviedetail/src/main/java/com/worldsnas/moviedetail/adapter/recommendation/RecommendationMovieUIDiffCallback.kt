package com.worldsnas.moviedetail.adapter.recommendation

import androidx.recyclerview.widget.DiffUtil
import com.worldsnas.moviedetail.model.MovieUIModel
import javax.inject.Inject

class RecommendationMovieUIDiffCallback @Inject constructor(
): DiffUtil.ItemCallback<MovieUIModel>() {
    override fun areItemsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean =
        oldItem == newItem
}