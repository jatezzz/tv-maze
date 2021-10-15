package com.jatezzz.tvmaze.peoplelist.response

import com.jatezzz.tvmaze.list.response.Links

data class Castcredit(
    val _links: Links,
    val self: Boolean,
    val voice: Boolean
)