package com.jatezzz.tvmaze.common.service

import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.list.response.SearchItem
import com.jatezzz.tvmaze.list.response.ShowsItem
import com.jatezzz.tvmaze.peoplelist.response.Person
import com.jatezzz.tvmaze.peoplelist.response.SearchPeople
import com.jatezzz.tvmaze.person.response.CreditsItem
import com.jatezzz.tvmaze.show.response.EpisodeItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val FETCH_SHOWS_LIST = "shows"
const val SEARCH_SHOWS_LIST = "search/shows"
const val FETCH_SHOW_DETAIL = "shows/{id}"
const val FETCH_SHOW_EPISODE = "shows/{id}/episodes"
const val FETCH_EPISODE_DETAIL = "episodes/{id}"
const val SEARCH_PEOPLE_DETAIL = "search/people"
const val FETCH_PERSON_DETAIL = "people/{id}"
const val FETCH_CAST_CREDIT_DETAIL = "people/{id}/castcredits?embed=show"

interface AssetsService {

    @GET(FETCH_SHOWS_LIST)
    suspend fun fetchShowsList(@Query("page") pageNumber: Int = 0): ResultType<List<ShowsItem>>

    @GET(SEARCH_SHOWS_LIST)
    suspend fun searchShowsList(@Query("q") name: String): ResultType<List<SearchItem>>

    @GET(FETCH_SHOW_DETAIL)
    suspend fun fetchShowDetail(@Path("id") id: Int): ResultType<ShowsItem>

    @GET(FETCH_SHOW_EPISODE)
    suspend fun fetchShowEpisodes(@Path("id") id: Int): ResultType<List<EpisodeItem>>

    @GET(FETCH_EPISODE_DETAIL)
    suspend fun fetchEpisodeDetail(@Path("id") id: Int): ResultType<EpisodeItem>

    @GET(SEARCH_PEOPLE_DETAIL)
    suspend fun searchPeople(@Query("q") name: String): ResultType<List<SearchPeople>>

    @GET(FETCH_PERSON_DETAIL)
    suspend fun fetchPersonById(@Path("id") id: Int): ResultType<Person>

    @GET(FETCH_CAST_CREDIT_DETAIL)
    suspend fun fetchCreditsByPersonId(@Path("id") id: Int): ResultType<List<CreditsItem>>

}
