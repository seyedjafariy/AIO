package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.ReviewEntity
import com.worldsnas.domain.model.servermodels.ReviewServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class ReviewServerEntityMapper @Inject constructor(
): Mapper<ReviewServerModel, ReviewEntity> {
    override fun map(item: ReviewServerModel): ReviewEntity =
        ReviewEntity(
            0,
            item.author,
            item.content,
            item.id,
            item.url
        )
}