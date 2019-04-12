package com.worldsnas.panther

import retrofit2.Response

interface RFetcher<T : Any?, R : Any> : Fetcher<T, Response<R>>