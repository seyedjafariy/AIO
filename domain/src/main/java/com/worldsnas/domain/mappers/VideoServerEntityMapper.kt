package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.VideoEntity
import com.worldsnas.domain.model.servermodels.VideoServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class VideoServerEntityMapper @Inject constructor(
) : Mapper<VideoServerModel, VideoEntity> {
    override fun map(item: VideoServerModel): VideoEntity =
        VideoEntity(
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