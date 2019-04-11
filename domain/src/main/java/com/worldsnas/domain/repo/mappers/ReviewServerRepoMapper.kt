package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.ReviewRepoModel
import com.worldsnas.domain.servermodels.ReviewServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class ReviewServerRepoMapper @Inject constructor(
): Mapper<ReviewServerModel, ReviewRepoModel> {
    override fun map(item: ReviewServerModel): ReviewRepoModel =
        ReviewRepoModel(
            item.author,
            item.content,
            item.id,
            item.url
        )
}