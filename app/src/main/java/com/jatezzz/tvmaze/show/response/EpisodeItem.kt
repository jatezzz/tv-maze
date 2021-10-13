package com.jatezzz.tvmaze.show.response

import com.jatezzz.tvmaze.list.response.Image
import com.jatezzz.tvmaze.list.response.Links

data class EpisodeItem(
    val _links: Links?,
    val airdate: String?,
    val airstamp: String?,
    val airtime: String?,
    val id: Int?,
    val image: Image?,
    val name: String?,
    val number: Int?,
    val runtime: Int?,
    val season: Int?,
    val summary: String?,
    val type: String?,
    val url: String?
)