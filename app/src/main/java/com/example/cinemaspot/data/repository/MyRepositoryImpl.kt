package com.example.cinemaspot.data.repository

import com.example.cinemaspot.data.Constants
import com.example.cinemaspot.data.models.movies.MovieDetailsResponse
import com.example.cinemaspot.data.models.movies.NowPlayingMoviesResponse
import com.example.cinemaspot.data.models.movies.PopularMoviesResponse
import com.example.cinemaspot.data.models.movies.TopRatedMoviesResponse
import com.example.cinemaspot.data.models.movies.UpComingMoviesResponse
import com.example.cinemaspot.data.remote.ApiService
import com.example.cinemaspot.domain.repository.MyRepository
import retrofit2.Response
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(private val apiService: ApiService) : MyRepository {
    override suspend fun fetchTopRatedMovies(page:Int): Response<TopRatedMoviesResponse> =
        apiService.fetchTopRatedMovies(Constants.API_KEY,page = page)

    override suspend fun fetchNowPlayingMovies(page: Int): Response<NowPlayingMoviesResponse> =
        apiService.fetchNowPlayingMovies(Constants.API_KEY,page=page)

    override suspend fun fetchPopularMovies(page: Int): Response<PopularMoviesResponse> =
        apiService.fetchPopularMovies(Constants.API_KEY,page=page)

    override suspend fun fetchUpComingMovies(page: Int): Response<UpComingMoviesResponse> =
        apiService.fetchUpComingMovies(Constants.API_KEY,page=page)

    override suspend fun fetchMovieDetails(movieId: Int): Response<MovieDetailsResponse> =
        apiService.fetchMovieDetails(movieId)
}