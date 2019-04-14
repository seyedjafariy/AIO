package com.worldsnas.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.worldsnas.home.model.MovieUIModel
import javax.inject.Inject

class MovieUIDiffCallback @Inject constructor(
): DiffUtil.ItemCallback<MovieUIModel>() {
    override fun areItemsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean =
        oldItem == newItem
}