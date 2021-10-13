package com.jatezzz.tvmaze.common.service

import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.list.response.SearchItem
import com.jatezzz.tvmaze.list.response.ShowsItem
import com.jatezzz.tvmaze.show.response.EpisodeItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val FETCH_SHOWS_LIST = "shows"
const val SEARCH_SHOWS_LIST = "search/shows"
const val FETCH_SHOW_DETAIL = "shows/{id}"
const val FETCH_SHOW_EPISODE = "shows/{id}/episodes"

interface AssetsService {

    @GET(FETCH_SHOWS_LIST)
    suspend fun fetchShowsList(@Query("page") pageNumber: Int = 0): ResultType<List<ShowsItem>>

    @GET(SEARCH_SHOWS_LIST)
    suspend fun searchShowsList(@Query("q") pageNumber: String): ResultType<List<SearchItem>>

    @GET(FETCH_SHOW_DETAIL)
    suspend fun fetchShowDetail(@Path("id") id: Int): ResultType<ShowsItem>

    @GET(FETCH_SHOW_EPISODE)
    suspend fun fetchShowEpisodes(@Path("id") id: Int): ResultType<List<EpisodeItem>>

}
