package com.worldsnas.moviedetail.view

import android.view.View
import android.widget.TextView
import com.worldsnas.base.BaseViewHolder
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.model.GenreUIModel

class GenreViewHolder(
    view: View
) : BaseViewHolder<GenreUIModel, MovieDetailIntent>(view) {

    lateinit var tvName : TextView

    override fun bind(obj: GenreUIModel) {
        tvName.text = obj.name
    }
}