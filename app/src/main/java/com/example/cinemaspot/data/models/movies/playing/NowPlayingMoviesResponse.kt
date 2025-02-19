package com.example.cinemaspot.data.models.movies.playing

import com.example.cinemaspot.data.models.movies.Dates
import com.example.cinemaspot.data.models.movies.Result

data class NowPlayingMoviesResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)