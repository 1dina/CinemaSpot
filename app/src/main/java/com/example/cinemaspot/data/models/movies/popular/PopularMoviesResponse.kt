package com.example.cinemaspot.data.models.movies.popular

import com.example.cinemaspot.data.models.movies.Result

data class PopularMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)