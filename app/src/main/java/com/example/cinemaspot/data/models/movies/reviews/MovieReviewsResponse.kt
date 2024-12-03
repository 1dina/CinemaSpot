package com.example.cinemaspot.data.models.movies.reviews

data class MovieReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ResultXXX>,
    val total_pages: Int,
    val total_results: Int
)