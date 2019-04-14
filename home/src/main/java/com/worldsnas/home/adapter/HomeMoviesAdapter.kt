package com.worldsnas.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.worldsnas.core.inflate
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.home.R
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.home.view.HomeMovieViewHolder
import javax.inject.Inject

@FeatureScope
class HomeMoviesAdapter @Inject constructor(
    diffCallback : MovieUIDiffCallback
): ListAdapter<MovieUIModel, HomeMovieViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieViewHolder =
        HomeMovieViewHolder(parent.inflate(R.layout.row_home_movie))

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}