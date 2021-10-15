package com.jatezzz.tvmaze.peoplelist.response

import com.jatezzz.tvmaze.list.response.Country
import com.jatezzz.tvmaze.list.response.Image
import com.jatezzz.tvmaze.list.response.Links

data class Person(
    val _embedded: Embedded?,
    val _links: Links?,
    val birthday: String?,
    val country: Country?,
    val deathday: Any?,
    val gender: String?,
    val id: Int?,
    val image: Image?,
    val name: String?,
    val updated: Int?,
    val url: String?
)