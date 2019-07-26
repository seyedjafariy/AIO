package com.worldsnas.domain.repo.people

import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class PeopleFetcher @Inject constructor(
    private val api: PeopleAPI
) : RFetcher<PeopleRequestModel, ResultsServerModel<PersonServerModel>> {

    override fun fetch(param: PeopleRequestModel): Single<Response<ResultsServerModel<PersonServerModel>>> =
            api.getPopularPeople(param.page)
                .errorHandler()
}