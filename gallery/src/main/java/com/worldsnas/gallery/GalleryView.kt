package com.worldsnas.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.worldsnas.base.ButterKnifeController
import com.worldsnas.core.getScreenWidth
import com.worldsnas.domain.helpers.coverFullUrl
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.navigation.model.GalleryImageType
import com.worldsnas.navigation.model.GalleryLocalModel

class GalleryView(
    bundle: Bundle
) : ButterKnifeController(bundle), OnDismissListener {

    private val localModel = bundle.getParcelable<GalleryLocalModel>(GalleryLocalModel.EXTRA_IMAGES)
        ?: throw NullPointerException("gallery local model can not be null")

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        val images = prepareImageUrls(container)

        return ImageViewerView(container.context).apply {
            //            setCustomImageRequestBuilder(builder.customImageRequestBuilder)
//            setCustomDraweeHierarchyBuilder(builder.customHierarchyBuilder)
            allowZooming(true)
            allowSwipeToDismiss(true)
            setOnDismissListener(this@GalleryView)
            setBackgroundColor(ContextCompat.getColor(container.context, R.color.defaultBackground))
            setUrls(images, localModel.startPosition)
//            setOverlayView(builder.overlayView)
//            setImageMargin(builder.imageMarginPixels)
//            setContainerPadding(builder.containerPaddingPixels)
//            setPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
//                override fun onPageSelected(position: Int) {
//                    if (builder.imageChangeListener != null) {
//                        builder.imageChangeListener.onImageChange(position)
//                    }
//                }
//            })
        }
    }

    private fun prepareImageUrls(container: ViewGroup) =
        with(container.context) {
            localModel.images.map {
                when (localModel.type) {
                    GalleryImageType.COVER ->
                        it.coverFullUrl(getScreenWidth())
                    GalleryImageType.POSTER ->
                        it.posterFullUrl(getScreenWidth())
                }
            }
        }


    override fun onDismiss() {
        router.popController(this)
    }
}