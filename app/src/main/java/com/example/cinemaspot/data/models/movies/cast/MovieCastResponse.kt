package com.example.cinemaspot.data.models.movies.cast

data class MovieCastResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)