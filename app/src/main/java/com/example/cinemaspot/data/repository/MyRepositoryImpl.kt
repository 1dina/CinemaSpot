package com.example.cinemaspot.data.repository

import com.example.cinemaspot.data.Constants
import com.example.cinemaspot.data.models.movies.details.MovieDetailsResponse
import com.example.cinemaspot.data.models.movies.reviews.MovieReviewsResponse
import com.example.cinemaspot.data.models.movies.playing.NowPlayingMoviesResponse
import com.example.cinemaspot.data.models.movies.popular.PopularMoviesResponse
import com.example.cinemaspot.data.models.movies.top.TopRatedMoviesResponse
import com.example.cinemaspot.data.models.movies.coming.UpComingMoviesResponse
import com.example.cinemaspot.data.models.movies.cast.MovieCastResponse
import com.example.cinemaspot.data.remote.ApiService
import com.example.cinemaspot.domain.repository.MyRepository
import retrofit2.Response
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(private val apiService: ApiService) : MyRepository {
    override suspend fun fetchTopRatedMovies(page: Int): Response<TopRatedMoviesResponse> =
        apiService.fetchTopRatedMovies(Constants.API_KEY, page = page)

    override suspend fun fetchNowPlayingMovies(page: Int): Response<NowPlayingMoviesResponse> =
        apiService.fetchNowPlayingMovies(Constants.API_KEY, page = page)

    override suspend fun fetchPopularMovies(page: Int): Response<PopularMoviesResponse> =
        apiService.fetchPopularMovies(Constants.API_KEY, page = page)

    override suspend fun fetchUpComingMovies(page: Int): Response<UpComingMoviesResponse> =
        apiService.fetchUpComingMovies(Constants.API_KEY, page = page)

    override suspend fun fetchMovieDetails(movieId: Int): Response<MovieDetailsResponse> =
        apiService.fetchMovieDetails(movieId)

    override suspend fun fetchMovieReviews(
        movieId: Int,
        page: Int
    ): Response<MovieReviewsResponse> = apiService.fetchMovieReviews(movieId = movieId, page = page)

    override suspend fun fetchMovieCasts(movieId: Int): Response<MovieCastResponse> =
        apiService.fetchMovieCasts(movieId)

}