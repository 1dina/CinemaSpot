package com.example.cinemaspot.data.models.movies.search

import com.example.cinemaspot.data.models.movies.Result

data class MovieSearchResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)