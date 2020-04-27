package com.worldsnas.home.view

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.worldsnas.base.pages
import com.worldsnas.base.ButterKnifeViewHolder
import com.worldsnas.home.R2
import com.worldsnas.home.adapter.HomeMoviesAdapter

class MoviesViewHolder(view: View) : ButterKnifeViewHolder(view) {

    @BindView(R2.id.movies)
    lateinit var moviesList: RecyclerView

    fun bind(adapter: HomeMoviesAdapter) {
        moviesList.layoutManager = GridLayoutManager(moviesList.context, 3)
        moviesList.adapter = adapter
    }

    fun pages() =
            moviesList.pages()

}