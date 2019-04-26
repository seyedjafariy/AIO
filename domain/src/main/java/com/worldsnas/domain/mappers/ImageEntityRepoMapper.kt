package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.ImageEntity
import com.worldsnas.domain.model.repomodel.ImageRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class ImageEntityRepoMapper @Inject constructor(
): Mapper<ImageEntity, ImageRepoModel> {
    override fun map(item: ImageEntity): ImageRepoModel =
        ImageRepoModel(
            item.id,
            item.aspectRatio,
            item.filePath,
            item.height,
            item.iso,
            item.voteAverage,
            item.voteCount,
            item.width
        )
}