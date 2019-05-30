package com.worldsnas.moviedetail.view

import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.worldsnas.base.BaseViewHolder
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.R2
import com.worldsnas.moviedetail.model.GenreUIModel

class GenreViewHolder(
    view: View
) : BaseViewHolder<GenreUIModel, MovieDetailIntent>(view) {

    @BindView(R2.id.tvName)
    lateinit var name : TextView

    override fun bind(obj: GenreUIModel) {
        name.text = obj.name
    }
}