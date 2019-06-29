package com.worldsnas.search.view

import android.net.Uri
import android.view.View
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.jakewharton.rxbinding2.view.clicks
import com.worldsnas.base.epoxyhelper.KotlinModel
import com.worldsnas.core.getDisplaySize
import com.worldsnas.core.transitionNameCompat
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.search.R
import com.worldsnas.search.SearchIntent
import com.worldsnas.search.SearchState
import com.worldsnas.search.model.MovieUIModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlin.math.roundToInt

class MovieRow(
    private val movie: MovieUIModel,
    private val presenter: MviPresenter<SearchIntent, SearchState>
) : KotlinModel(
    R.layout.row_search_movie
) {
    private val disposables = CompositeDisposable()

    private val poster by bind<SimpleDraweeView>(R.id.poster)
    private val title by bind<TextView>(R.id.title)
    private val releaseDate by bind<TextView>(R.id.releaseDate)


    override fun bind() {
        val width = poster.getDisplaySize().width / 3
        val height = (width * 1.5).roundToInt()

        val url = movie.poster posterFullUrl width

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setResizeOptions(ResizeOptions.forDimensions(width, height))
            .build()
        poster.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(poster.controller)
            .setImageRequest(request)
            .build()

        title.text = movie.title
        releaseDate.text = movie.releaseDate

        poster.transitionNameCompat = poster
            .context
            .getString(
                R.string.transition_img_1_id,
                movie.id
            )
        title.transitionNameCompat = title
            .context
            .getString(
                R.string.transition_txt_1_id,
                movie.id
            )

        releaseDate.transitionNameCompat = releaseDate
            .context
            .getString(
                R.string.transition_txt_2_id,
                movie.id
            )
    }

    override fun onViewAttachedToWindow(view: View) {
        super.onViewAttachedToWindow(view)
        view.clicks()
            .map {
                SearchIntent.SearchResultClicked(
                    movie,
                    poster.transitionNameCompat ?: "",
                    title.transitionNameCompat ?: "",
                    releaseDate.transitionNameCompat ?: ""
                )
            }
            .subscribeBy {
                presenter.processIntents(it)
            }
            .addTo(disposables)
    }

    override fun onViewDetachedFromWindow(view: View) {
        super.onViewDetachedFromWindow(view)
        disposables.clear()
    }
}