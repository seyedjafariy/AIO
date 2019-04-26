package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.ReviewEntity
import com.worldsnas.domain.model.repomodel.ReviewRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class ReviewEntityRepoMapper @Inject constructor(
) : Mapper<ReviewEntity, ReviewRepoModel> {
    override fun map(item: ReviewEntity): ReviewRepoModel =
        ReviewRepoModel(
            item.id,
            item.author,
            item.content,
            item.reviewId,
            item.url
        )
}