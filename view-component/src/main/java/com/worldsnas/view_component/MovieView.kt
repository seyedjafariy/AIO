package com.worldsnas.view_component

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.*
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.worldsnas.base.getDisplaySize
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.view_component.databinding.MovieViewBinding
import kotlin.math.roundToInt

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class MovieView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {
    private val binding: MovieViewBinding =
        MovieViewBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp
    lateinit var movie: Movie

    private fun poster(posterUrl: String) {
        val width = binding.root.getDisplaySize().width / 3
        val height = (width * 1.5).roundToInt()

        val url = posterUrl posterFullUrl width

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setResizeOptions(ResizeOptions.forDimensions(width, height))
            .build()
        binding.moviePoster.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(binding.moviePoster.controller)
            .setImageRequest(request)
            .build()
    }

    private fun title(title: CharSequence) {
        binding.movieTitle.text = title
    }

    private fun releaseDate(date: CharSequence) {
        binding.movieReleaseDate.text = date
    }

    @AfterPropsSet
    fun movieModel() {
        poster(movie.poster)
        title(movie.title)
        releaseDate(movie.releaseDate)
    }

    @CallbackProp
    fun listener(listener: ((movie: Movie) -> Unit)?) {
        if (listener == null) {
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener {
                listener(movie)
            }
        }
    }
}

data class Movie(
    val id: Long,
    val poster: String,
    val cover: String,
    val title: String,
    val releaseDate: String
)
