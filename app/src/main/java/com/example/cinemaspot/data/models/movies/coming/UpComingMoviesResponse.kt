package com.example.cinemaspot.data.models.movies.coming

import com.example.cinemaspot.data.models.movies.DatesX
import com.example.cinemaspot.data.models.movies.Result

data class UpComingMoviesResponse(
    val dates: DatesX,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)