package com.example.cinemaspot.data.models.movies

data class UpComingMoviesResponse(
    val dates: DatesX,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)