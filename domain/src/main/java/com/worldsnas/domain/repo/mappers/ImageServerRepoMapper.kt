package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.ImageRepoModel
import com.worldsnas.domain.servermodels.ImageServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class ImageServerRepoMapper @Inject constructor(
): Mapper<ImageServerModel, ImageRepoModel> {
    override fun map(item: ImageServerModel): ImageRepoModel =
        ImageRepoModel(
            item.aspectRatio,
            item.filePath,
            item.height,
            item.iso,
            item.voteAverage,
            item.voteCount,
            item.width
        )
}