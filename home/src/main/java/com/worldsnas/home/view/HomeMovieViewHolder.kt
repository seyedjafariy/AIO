package com.worldsnas.home.view

import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.facebook.drawee.view.SimpleDraweeView
import com.worldsnas.base.BaseViewHolder
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.R2
import com.worldsnas.home.model.MovieUIModel

class HomeMovieViewHolder(view: View) : BaseViewHolder<MovieUIModel, HomeIntent>(view) {

    @BindView(R2.id.poster)
    lateinit var poster: SimpleDraweeView
    @BindView(R2.id.title)
    lateinit var title: TextView
    @BindView(R2.id.releaseDate)
    lateinit var releaseDate: TextView

    override fun bind(obj: MovieUIModel) {
        poster.setImageURI(obj.poster)
        title.text = obj.title
        releaseDate.text = obj.releaseDate
    }
}