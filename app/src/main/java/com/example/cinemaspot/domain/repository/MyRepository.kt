package com.example.cinemaspot.domain.repository

import com.example.cinemaspot.data.models.movies.details.MovieDetailsResponse
import com.example.cinemaspot.data.models.movies.reviews.MovieReviewsResponse
import com.example.cinemaspot.data.models.movies.playing.NowPlayingMoviesResponse
import com.example.cinemaspot.data.models.movies.popular.PopularMoviesResponse
import com.example.cinemaspot.data.models.movies.top.TopRatedMoviesResponse
import com.example.cinemaspot.data.models.movies.coming.UpComingMoviesResponse
import com.example.cinemaspot.data.models.movies.cast.MovieCastResponse
import retrofit2.Response

interface MyRepository {
    suspend fun fetchTopRatedMovies(page: Int): Response<TopRatedMoviesResponse>
    suspend fun fetchNowPlayingMovies(page: Int):Response<NowPlayingMoviesResponse>
    suspend fun fetchPopularMovies(page: Int) : Response<PopularMoviesResponse>
    suspend fun fetchUpComingMovies(page: Int) : Response<UpComingMoviesResponse>
    suspend fun fetchMovieDetails(movieId : Int):Response<MovieDetailsResponse>
    suspend fun fetchMovieReviews(movieId: Int,page: Int):Response<MovieReviewsResponse>
    suspend fun fetchMovieCasts(movieId: Int):Response<MovieCastResponse>
}