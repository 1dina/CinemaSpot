package com.example.cinemaspot.data.models.movies.top

import com.example.cinemaspot.data.models.movies.Result


data class TopRatedMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)