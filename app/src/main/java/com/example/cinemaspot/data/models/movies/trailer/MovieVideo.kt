package com.example.cinemaspot.data.models.movies.trailer

data class MovieVideo(
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val published_at: String,
    val id: String
)