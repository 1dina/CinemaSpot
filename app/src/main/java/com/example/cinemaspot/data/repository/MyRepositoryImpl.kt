package com.example.cinemaspot.data.repository

import com.example.cinemaspot.data.Constants
import com.example.cinemaspot.data.models.TopRatedMoviesResponse
import com.example.cinemaspot.data.remote.ApiService
import com.example.cinemaspot.domain.repository.MyRepository
import retrofit2.Response
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(val apiService: ApiService) : MyRepository {
    override suspend fun fetchTopRatedMovies(page:Int): Response<TopRatedMoviesResponse> =
        apiService.getTopRatedMovies(Constants.API_KEY,page = page)
}