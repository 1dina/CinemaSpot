package com.example.cinemaspot.data.remote

import com.example.cinemaspot.data.Constants
import com.example.cinemaspot.data.models.movies.MovieDetailsResponse
import com.example.cinemaspot.data.models.movies.reviews.MovieReviewsResponse
import com.example.cinemaspot.data.models.movies.NowPlayingMoviesResponse
import com.example.cinemaspot.data.models.movies.PopularMoviesResponse
import com.example.cinemaspot.data.models.movies.TopRatedMoviesResponse
import com.example.cinemaspot.data.models.movies.UpComingMoviesResponse
import com.example.cinemaspot.data.models.movies.cast.MovieCastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.TOP_RATED_MOVIE_ENDPOINT)
    suspend fun fetchTopRatedMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<TopRatedMoviesResponse>

    @GET(Constants.NOW_PLAYING_ENDPOINT)
    suspend fun fetchNowPlayingMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<NowPlayingMoviesResponse>

    @GET(Constants.POPULAR_MOVIES_ENDPOINT)
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<PopularMoviesResponse>

    @GET(Constants.UP_COMING_MOVIES_ENDPOINT)
    suspend fun fetchUpComingMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<UpComingMoviesResponse>

    @GET(Constants.MOVIE_DETAILS_ENDPOINT)
    suspend fun fetchMovieDetails(
        @Path ("movie_id") movieId : Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US"
    ): Response<MovieDetailsResponse>

    @GET(Constants.MOVIE_REVIEWS_ENDPOINT)
    suspend fun fetchMovieReviews(
        @Path ("movie_id") movieId : Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<MovieReviewsResponse>

    @GET(Constants.MOVIE_CAST_ENDPOINT)
    suspend fun fetchMovieCasts(
        @Path ("movie_id") movieId : Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<MovieCastResponse>



}