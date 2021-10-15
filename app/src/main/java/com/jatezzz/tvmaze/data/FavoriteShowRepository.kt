package com.jatezzz.tvmaze.data

import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.common.retrofit.succeeded
import com.jatezzz.tvmaze.list.response.ShowsItem
import javax.inject.Inject

class FavoriteShowRepository @Inject constructor(
    private val favoriteShowsLocalDataSource: FavoriteShowsLocalDataSource
) {

    suspend fun retrieveFavoriteById(showId: Long): ResultType<FavoriteShow?> {
        val fetchFavoriteShowList = favoriteShowsLocalDataSource.fetchFavoriteShowById(showId)
        if (fetchFavoriteShowList.succeeded) {
            return ResultType.Success((fetchFavoriteShowList as ResultType.Success).data)
        }
        return fetchFavoriteShowList as ResultType.Error
    }

    suspend fun retrieveFavoriteShowList(
        fromLocal: Boolean,
        userId: Long = 0
    ): ResultType<List<FavoriteShow>> {
        val fetchFavoriteShowList = favoriteShowsLocalDataSource.fetchFavoriteShowList()
        if (fetchFavoriteShowList.succeeded) {
            return ResultType.Success((fetchFavoriteShowList as ResultType.Success).data)
        }
        return fetchFavoriteShowList as ResultType.Error
    }

    suspend fun saveFavoriteShow(show: ShowsItem) {
        favoriteShowsLocalDataSource.saveFavoriteShow(
            FavoriteShow(
                show.id,
                show.name ?: "",
                show.image?.medium ?: ""
            )
        )
    }

    suspend fun removeFavoriteShow(showId: Int) {
        favoriteShowsLocalDataSource.deleteFavoriteShowById(showId)
    }

}