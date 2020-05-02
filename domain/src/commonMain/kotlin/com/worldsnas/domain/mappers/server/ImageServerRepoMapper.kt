package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.ImageServerTypeHolder
import com.worldsnas.domain.model.repomodel.ImageRepoModel

class ImageServerRepoMapper : Mapper<ImageServerTypeHolder, ImageRepoModel> {
    override fun map(item: ImageServerTypeHolder): ImageRepoModel =
        ImageRepoModel(
            0,
            item.image.aspectRatio,
            item.image.filePath,
            item.image.height,
            item.image.iso ?: "",
            item.image.voteAverage,
            item.image.voteCount,
            item.image.width
        )
}