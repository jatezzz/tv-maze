package com.jatezzz.tvmaze.common.service

import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.main.response.ShowsItem
import retrofit2.http.GET
import retrofit2.http.Headers

const val FETCH_SHOWS_LIST = "shows"

interface AssetsService {

  @GET(FETCH_SHOWS_LIST)
  @Headers("Content-Type: application/json")
  suspend fun fetchShowsList(): ResultType<List<ShowsItem>>

}
