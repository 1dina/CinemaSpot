package com.example.cinemaspot.domain.repository

import com.example.cinemaspot.data.models.TopRatedMoviesResponse
import retrofit2.Response

interface MyRepository {
    suspend fun fetchTopRatedMovies(page: Int): Response<TopRatedMoviesResponse>
}