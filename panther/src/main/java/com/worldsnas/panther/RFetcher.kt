package com.worldsnas.panther

import retrofit2.Response

interface RFetcher<in T : Any?, R : Any> : Fetcher<T, Response<R>>