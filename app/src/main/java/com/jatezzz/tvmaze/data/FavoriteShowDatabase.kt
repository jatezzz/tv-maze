package com.jatezzz.tvmaze.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room Database that contains the Scenes table.
 *
 * Note that exportSchema should be true in favoriteShowion databases.
 */
@Database(
  entities = [FavoriteShow::class],
  version = 1,
  exportSchema = false
)

abstract class FavoriteShowDatabase : RoomDatabase() {

  abstract fun sceneDao(): FavoriteShowDao

  suspend fun getFavoriteShows(): List<FavoriteShow> {
    return sceneDao().getFavoriteShows()
  }

  suspend fun getFavoriteShowById(showId: Long): FavoriteShow? {
    return sceneDao().getFavoriteShows(showId)
  }

  suspend fun insertFavoriteShows(favoriteShow: FavoriteShow) {
    sceneDao().insertIndividual(favoriteShow)
  }

  suspend fun deleteFavoriteShowById(showId: Int) {
    sceneDao().deleteById(showId)
  }
}
