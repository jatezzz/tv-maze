package com.jatezzz.tvmaze.person.response

data class CreditsItem(
    val _embedded: Embedded,
    val _links: LinksX,
    val self: Boolean,
    val voice: Boolean
)