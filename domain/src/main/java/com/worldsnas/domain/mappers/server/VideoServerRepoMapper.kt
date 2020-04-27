package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.model.repomodel.VideoRepoModel
import com.worldsnas.domain.model.servermodels.VideoServerModel
import com.worldsnas.core.Mapper
import javax.inject.Inject

class VideoServerRepoMapper @Inject constructor(
): Mapper<VideoServerModel, VideoRepoModel> {
    override fun map(item: VideoServerModel): VideoRepoModel =
        VideoRepoModel(
            0,
            item.id,
            item.iso_639_1,
            item.iso_3166_1,
            item.key,
            item.name,
            item.site,
            item.size,
            item.type
        )
}