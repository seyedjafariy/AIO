package com.worldsnas.domain.repo.home.trending.model

import com.worldsnas.domain.servermodels.error.ErrorServerModel

sealed class TrendingRepoOutputModel {

    class Error(val err : ErrorServerModel)
}