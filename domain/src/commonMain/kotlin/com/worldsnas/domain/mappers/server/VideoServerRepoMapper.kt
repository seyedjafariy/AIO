package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.VideoRepoModel
import com.worldsnas.domain.model.servermodels.VideoServerModel

class VideoServerRepoMapper : Mapper<VideoServerModel, VideoRepoModel> {
    override fun map(item: VideoServerModel): VideoRepoModel =
        VideoRepoModel(
            0,
            item.id,
            item.iso_639_1 ?: "",
            item.iso_3166_1 ?: "",
            item.key,
            item.name,
            item.site,
            item.size,
            item.type
        )
}