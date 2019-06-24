package com.worldsnas.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.worldsnas.home.model.HomeUIModel
import javax.inject.Inject

class HomeUIDiffCallback @Inject constructor(
        private val movieDiffCallback: MovieUIDiffCallback
) : DiffUtil.ItemCallback<HomeUIModel>() {
    override fun areItemsTheSame(oldItem: HomeUIModel, newItem: HomeUIModel): Boolean =
            if (oldItem is HomeUIModel.Slider && newItem is HomeUIModel.Slider) {
                true
            } else if (oldItem is HomeUIModel.LatestMovie && newItem is HomeUIModel.LatestMovie) {
                movieDiffCallback.areItemsTheSame(oldItem.movie, newItem.movie)
            } else {
                false
            }

    override fun areContentsTheSame(oldItem: HomeUIModel, newItem: HomeUIModel): Boolean =
            if (oldItem is HomeUIModel.Slider && newItem is HomeUIModel.Slider) {
                if (oldItem.slides.isEmpty()) {
                    false
                } else {
                    oldItem.slides.size == newItem.slides.size
                }
            } else if (oldItem is HomeUIModel.LatestMovie && newItem is HomeUIModel.LatestMovie) {
                movieDiffCallback.areContentsTheSame(oldItem.movie, newItem.movie)
            } else {
                false
            }
}