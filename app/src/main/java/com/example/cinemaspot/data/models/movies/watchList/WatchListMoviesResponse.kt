package com.example.cinemaspot.data.models.movies.watchList

data class WatchListMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)