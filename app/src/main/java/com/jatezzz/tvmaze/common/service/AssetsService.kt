package com.jatezzz.tvmaze.common.service

import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.list.response.ShowsItem
import retrofit2.http.GET
import retrofit2.http.Query

const val FETCH_SHOWS_LIST = "shows"

interface AssetsService {

    @GET(FETCH_SHOWS_LIST)
    suspend fun fetchShowsList(@Query("page") pageNumber: Int = 0): ResultType<List<ShowsItem>>

}
