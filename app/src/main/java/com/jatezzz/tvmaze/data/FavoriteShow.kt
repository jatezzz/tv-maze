package com.jatezzz.tvmaze.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteShows")
class FavoriteShow(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    val name: String = "",
    val imageUrl: String = ""
)
