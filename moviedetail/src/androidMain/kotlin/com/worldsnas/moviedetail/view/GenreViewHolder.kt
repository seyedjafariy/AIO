package com.worldsnas.moviedetail.view

import android.view.View
import android.widget.TextView
import com.worldsnas.base.BaseViewHolder
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.databinding.RowGenreBinding
import com.worldsnas.moviedetail.model.GenreUIModel

class GenreViewHolder(
    view: View
) : BaseViewHolder<GenreUIModel, MovieDetailIntent>(view) {

    private val tvName : TextView = RowGenreBinding.bind(view).tvName

    override fun bind(obj: GenreUIModel) {
        tvName.text = obj.name
    }
}