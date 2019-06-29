package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.model.repomodel.ReviewRepoModel
import com.worldsnas.domain.model.servermodels.ReviewServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class ReviewServerRepoMapper @Inject constructor(
): Mapper<ReviewServerModel, ReviewRepoModel> {
    override fun map(item: ReviewServerModel): ReviewRepoModel =
        ReviewRepoModel(
            0,
            item.author,
            item.content,
            item.id,
            item.url
        )
}