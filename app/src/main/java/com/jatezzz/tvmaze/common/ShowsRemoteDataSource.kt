package com.jatezzz.tvmaze.common

import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.common.service.AssetsService
import com.jatezzz.tvmaze.list.response.SearchItem
import com.jatezzz.tvmaze.list.response.ShowsItem
import com.jatezzz.tvmaze.peoplelist.response.SearchPeople
import com.jatezzz.tvmaze.show.response.EpisodeItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowsRemoteDataSource internal constructor(
  private val assetsService: AssetsService,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchShowsList(page: Int): ResultType<List<ShowsItem>> = withContext(ioDispatcher) {
        return@withContext assetsService.fetchShowsList(page)
    }

    suspend fun retrieveListByInput(text: String): ResultType<List<SearchItem>> =
        withContext(ioDispatcher) {
            return@withContext assetsService.searchShowsList(text)
        }

    suspend fun retrieveShowDetail(id: Int): ResultType<ShowsItem> =
        withContext(ioDispatcher) {
            return@withContext assetsService.fetchShowDetail(id)
        }

    suspend fun retrieveShowEpisodes(id: Int): ResultType<List<EpisodeItem>> =
        withContext(ioDispatcher) {
            return@withContext assetsService.fetchShowEpisodes(id)
        }

    suspend fun retrieveEpisodeDetail(id: Int): ResultType<EpisodeItem> =
        withContext(ioDispatcher) {
            return@withContext assetsService.fetchEpisodeDetail(id)
        }

    suspend fun searchPeople(name: String): ResultType<List<SearchPeople>> =
        withContext(ioDispatcher) {
            return@withContext assetsService.searchPeople(name)
        }

}
