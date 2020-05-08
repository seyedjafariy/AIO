package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.ReviewRepoModel
import com.worldsnas.domain.model.servermodels.ReviewServerModel

class ReviewServerRepoMapper : Mapper<ReviewServerModel, ReviewRepoModel> {
    override fun map(item: ReviewServerModel): ReviewRepoModel =
        ReviewRepoModel(
            0,
            item.author,
            item.content,
            item.id,
            item.url
        )
}