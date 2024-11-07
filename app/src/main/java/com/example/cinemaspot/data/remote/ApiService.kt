package com.example.cinemaspot.data.remote

import com.example.cinemaspot.data.Constants
import com.example.cinemaspot.data.models.TopRatedMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.TOP_RATED_MOVIE_ENDPOINT)
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<TopRatedMoviesResponse>
}