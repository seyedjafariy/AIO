package com.worldsnas.moviedetail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.worldsnas.moviedetail.model.GenreUIModel
import javax.inject.Inject

class GenreDiffCallback @Inject constructor(
): DiffUtil.ItemCallback<GenreUIModel>() {
    override fun areItemsTheSame(oldItem: GenreUIModel, newItem: GenreUIModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GenreUIModel, newItem: GenreUIModel): Boolean =
            oldItem.name == newItem.name
}