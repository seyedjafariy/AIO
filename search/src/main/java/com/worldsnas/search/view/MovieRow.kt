package com.worldsnas.search.view

import android.net.Uri
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.jakewharton.rxbinding3.view.clicks
import com.worldsnas.base.getDisplaySize
import com.worldsnas.base.transitionNameCompat
import com.worldsnas.base.epoxyhelper.KotlinModel
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

    @BindView(R.id.poster)
    lateinit var poster: SimpleDraweeView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.releaseDate)
    lateinit var releaseDate: TextView

    override fun viewBound(view: View) {
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
                com.worldsnas.base.R.string.transition_img_1_id,
                movie.id
            )
        title.transitionNameCompat = title
            .context
            .getString(
                com.worldsnas.base.R.string.transition_txt_1_id,
                movie.id
            )

        releaseDate.transitionNameCompat = releaseDate
            .context
            .getString(
                com.worldsnas.base.R.string.transition_txt_2_id,
                movie.id
            )
    }

    private fun actions(){
        view?.clicks()
            ?.map {
                SearchIntent.SearchResultClicked(
                    movie,
                    poster.transitionNameCompat ?: "",
                    title.transitionNameCompat ?: "",
                    releaseDate.transitionNameCompat ?: ""
                )
            }
            ?.subscribeBy {
                presenter.processIntents(it)
            }
            ?.addTo(disposables)
    }

    override fun onViewAttachedToWindow(view: View) {
        super.onViewAttachedToWindow(view)
        actions()
    }

    override fun onViewDetachedFromWindow(view: View) {
        super.onViewDetachedFromWindow(view)
        disposables.clear()
    }
}