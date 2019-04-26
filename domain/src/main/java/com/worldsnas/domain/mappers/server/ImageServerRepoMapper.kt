package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.helpers.ImageInfo
import com.worldsnas.domain.model.ImageServerTypeHolder
import com.worldsnas.domain.model.repomodel.ImageRepoModel
import com.worldsnas.panther.Factory
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class ImageServerRepoMapper @Inject constructor(
    private val urlFactory: Factory<ImageInfo, String>
) : Mapper<ImageServerTypeHolder, ImageRepoModel> {
    override fun map(item: ImageServerTypeHolder): ImageRepoModel =
        ImageRepoModel(
            0,
            item.image.aspectRatio,
            urlFactory.create(
                ImageInfo(
                    item.type,
                    item.image.filePath
                )
            ),
            item.image.height,
            item.image.iso,
            item.image.voteAverage,
            item.image.voteCount,
            item.image.width
        )
}