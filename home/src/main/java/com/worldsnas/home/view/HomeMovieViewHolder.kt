package com.worldsnas.home.view

import android.view.View
import com.worldsnas.base.BaseViewHolder
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.model.MovieUIModel
import kotlinx.android.synthetic.main.row_home_movie.*

class HomeMovieViewHolder (view : View): BaseViewHolder<MovieUIModel, HomeIntent>(view) {

    override fun bind(obj : MovieUIModel){
        poster.setImageURI(obj.poster)
        title.text = obj.title
        releaseDate.text = obj.releaseDate
    }

}