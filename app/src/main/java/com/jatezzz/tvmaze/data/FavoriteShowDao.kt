/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jatezzz.tvmaze.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteShowDao {

  @Query("SELECT * FROM favoriteShows")
  suspend fun getFavoriteShows(): List<FavoriteShow>

  @Query("SELECT * FROM favoriteShows WHERE id = :favoriteShowId")
  suspend fun getFavoriteShows(favoriteShowId: Long): FavoriteShow?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertIndividual(entity: FavoriteShow): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMultipleRecord(entityList: List<FavoriteShow>)

  @Update
  suspend fun updateIndividual(entity: FavoriteShow): Int

  @Update
  suspend fun updateMultipleRecord(entityList: List<FavoriteShow>): Int

  @Query("DELETE FROM favoriteShows WHERE id = :id")
  suspend fun deleteById(id: Int): Int
}
