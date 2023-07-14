package com.bibi.animestream.model

data class Anime(
    val id: Int,
    val title: String,
    val genre: String,
    val synopsis: String,
    val imageUrl: Int,
    val rating: Double,
    var isFavorite: Boolean = false
)