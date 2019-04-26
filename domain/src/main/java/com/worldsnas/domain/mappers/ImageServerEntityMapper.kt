package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.ImageEntity
import com.worldsnas.domain.model.servermodels.ImageServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class ImageServerEntityMapper @Inject constructor(
): Mapper<ImageServerModel, ImageEntity> {
    override fun map(item: ImageServerModel): ImageEntity =
        ImageEntity(
            0,
            item.aspectRatio,
            item.filePath,
            item.height,
            item.iso,
            item.voteAverage,
            item.voteCount,
            item.width
        )
}