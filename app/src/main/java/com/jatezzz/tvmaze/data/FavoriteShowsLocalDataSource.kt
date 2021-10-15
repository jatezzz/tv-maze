package com.jatezzz.tvmaze.data

import com.jatezzz.tvmaze.common.retrofit.ResultType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteShowsLocalDataSource internal constructor(
    private val sceneDatabase: FavoriteShowDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchFavoriteShowList() = withContext(ioDispatcher) {
        try {
            val sceneList = sceneDatabase.getFavoriteShows()
            return@withContext ResultType.Success(sceneList)
        } catch (e: Exception) {
            return@withContext ResultType.Error(e)
        }
    }

    suspend fun fetchFavoriteShowById(showId: Long) = withContext(ioDispatcher) {
        try {
            val sceneList = sceneDatabase.getFavoriteShowById(showId)
            return@withContext ResultType.Success(sceneList)
        } catch (e: Exception) {
            return@withContext ResultType.Error(e)
        }
    }

    suspend fun saveFavoriteShow(favoriteShow: FavoriteShow) =
        withContext(ioDispatcher) {
            return@withContext sceneDatabase.insertFavoriteShows(favoriteShow)
        }

    suspend fun deleteFavoriteShowById(showId: Int) =
        withContext(ioDispatcher) {
            return@withContext sceneDatabase.deleteFavoriteShowById(showId)
        }

}
