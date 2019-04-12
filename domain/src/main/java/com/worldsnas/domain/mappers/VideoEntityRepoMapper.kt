package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.VideoEntity
import com.worldsnas.domain.repomodel.VideoRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class VideoEntityRepoMapper @Inject constructor(
) : Mapper<VideoEntity, VideoRepoModel> {
    override fun map(item: VideoEntity): VideoRepoModel =
        VideoRepoModel(
            item.id,
            item.videoId,
            item.iso_639_1,
            item.iso_3166_1,
            item.key,
            item.name,
            item.site,
            item.size,
            item.type
        )
}