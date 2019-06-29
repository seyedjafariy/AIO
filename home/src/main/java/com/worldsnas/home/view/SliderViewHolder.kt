package com.worldsnas.home.view

import android.view.View
import androidx.core.os.bundleOf
import butterknife.BindView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.worldsnas.base.ButterKnifeViewHolder
import com.worldsnas.core.getScreenWidth
import com.worldsnas.domain.helpers.coverFullUrl
import com.worldsnas.home.R2
import com.worldsnas.home.model.MovieUIModel

class SliderViewHolder(view: View) : ButterKnifeViewHolder(view) {

    @BindView(R2.id.slider)
    lateinit var slider: SliderLayout

    fun bind(
            movies: List<MovieUIModel>,
            sliderListener: BaseSliderView.OnSliderClickListener) {
        itemView.context.run {
            slider.removeAllSliders()
            movies.forEach {
                slider.addSlider(
                        TextSliderView(this)
                                .description(it.title)
                                .image(it.cover.coverFullUrl(getScreenWidth()))
                                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                .bundle(
                                        bundleOf(
                                                "id" to it.id,
                                                "url" to it.cover.coverFullUrl(getScreenWidth())
                                        )
                                )
                                .setOnSliderClickListener(sliderListener)
                )
                slider.setPresetTransformer(SliderLayout.Transformer.Default)
            }
        }
    }

    fun stopAutoCycle() =
            slider.stopAutoCycle()

    fun startAutoCycle() =
            slider.startAutoCycle()
}
