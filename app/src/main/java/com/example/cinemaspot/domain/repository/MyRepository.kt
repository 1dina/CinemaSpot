package com.example.cinemaspot.domain.repository

import com.example.cinemaspot.data.models.movies.MovieDetailsResponse
import com.example.cinemaspot.data.models.movies.NowPlayingMoviesResponse
import com.example.cinemaspot.data.models.movies.PopularMoviesResponse
import com.example.cinemaspot.data.models.movies.TopRatedMoviesResponse
import com.example.cinemaspot.data.models.movies.UpComingMoviesResponse
import retrofit2.Response

interface MyRepository {
    suspend fun fetchTopRatedMovies(page: Int): Response<TopRatedMoviesResponse>
    suspend fun fetchNowPlayingMovies(page: Int):Response<NowPlayingMoviesResponse>
    suspend fun fetchPopularMovies(page: Int) : Response<PopularMoviesResponse>
    suspend fun fetchUpComingMovies(page: Int) : Response<UpComingMoviesResponse>
    suspend fun fetchMovieDetails(movieId : Int):Response<MovieDetailsResponse>
}