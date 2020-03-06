package com.worldsnas.view_component

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.*
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.worldsnas.androidcore.getDisplaySize
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.view_component.databinding.MovieViewBinding
import kotlin.math.roundToInt

@ModelView(defaultLayout = R2.layout.movie_view, saveViewState = true)
class MovieView(context: Context) : LinearLayout(context) {
    private val binding: MovieViewBinding =
        MovieViewBinding.inflate(LayoutInflater.from(context), this, false)

    @ModelProp
    lateinit var movie: Movie

    @ModelProp
    fun poster(posterUrl: String) {
        val width = binding.root.getDisplaySize().width / 3
        val height = (width * 1.5).roundToInt()

        val url = posterUrl posterFullUrl width

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setResizeOptions(ResizeOptions.forDimensions(width, height))
            .build()
        binding.poster.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(binding.poster.controller)
            .setImageRequest(request)
            .build()
    }

    @TextProp
    fun title(title: CharSequence) {
        binding.title.text = title
    }

    @TextProp
    fun releaseDate(date: CharSequence) {
        binding.releaseDate.text = date
    }

    @AfterPropsSet
    fun movieModel() {
        poster(movie.poster)
        title(movie.title)
        releaseDate(movie.releaseDate)
    }

    @CallbackProp
    fun listener(listener : OnClickListener?){

    }
}

data class Movie(
    val id: Long,
    val poster: String,
    val cover: String,
    val title: String,
    val releaseDate: String
)
