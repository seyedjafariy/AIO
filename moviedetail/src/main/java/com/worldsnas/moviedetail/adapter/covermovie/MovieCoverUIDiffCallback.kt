package com.worldsnas.moviedetail.adapter.covermovie

import androidx.recyclerview.widget.DiffUtil
import com.worldsnas.moviedetail.model.MovieUIModel
import javax.inject.Inject

class MovieCoverUIDiffCallback @Inject constructor(
): DiffUtil.ItemCallback<MovieUIModel>() {
    override fun areItemsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean =
        oldItem == newItem
}