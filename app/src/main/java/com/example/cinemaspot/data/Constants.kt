package com.example.cinemaspot.data

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val API_KEY = "42edc881967152614d536bbcc26ba453"
    const val TOP_RATED_MOVIE_ENDPOINT = "3/movie/top_rated"
    const val NOW_PLAYING_ENDPOINT = "3/movie/now_playing"
    const val POPULAR_MOVIES_ENDPOINT = "3/movie/popular"
    const val UP_COMING_MOVIES_ENDPOINT = "3/movie/upcoming"
    const val MOVIE_DETAILS_ENDPOINT = "3/movie/{movie_id}"
    const val MOVIE_REVIEWS_ENDPOINT = "3/movie/{movie_id}/reviews"
    const val MOVIE_CAST_ENDPOINT = "3/movie/{movie_id}/credits"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    const val GET_REQUEST_TOKEN = "3/authentication/token/new"
    const val VALIDATE_LOGIN = "3/authentication/token/validate_with_login"
    const val CREATE_SESSION = "3/authentication/session/new"
    const val WATCHLIST_MOVIE_ENDPOINT = "3/account/null/watchlist/movies"
    const val ADD_MOVIE_TO_WATCHLIST = "3/account/null/watchlist"
    const val MOVIE_TRAILER_ENDPOINT = "3/movie/{movie_id}/videos"
    const val SEARCH_MOVIE_ENDPOINT ="3/search/movie"
}