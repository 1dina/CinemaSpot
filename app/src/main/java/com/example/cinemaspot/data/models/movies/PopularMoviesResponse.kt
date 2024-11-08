package com.example.cinemaspot.data.models.movies

data class PopularMoviesResponse(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)