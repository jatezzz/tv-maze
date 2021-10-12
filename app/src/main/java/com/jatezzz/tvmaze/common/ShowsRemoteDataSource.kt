package com.jatezzz.tvmaze.common

import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.common.service.AssetsService
import com.jatezzz.tvmaze.main.response.ShowsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowsRemoteDataSource internal constructor(
  private val assetsService: AssetsService,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

  suspend fun fetchShowsList(): ResultType<List<ShowsItem>> = withContext(ioDispatcher) {
    return@withContext assetsService.fetchShowsList()
  }

}
