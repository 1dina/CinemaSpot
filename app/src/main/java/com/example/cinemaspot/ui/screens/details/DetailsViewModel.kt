package com.example.cinemaspot.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.models.movies.cast.MovieCastResponse
import com.example.cinemaspot.data.models.movies.details.MovieDetailsResponse
import com.example.cinemaspot.data.models.movies.reviews.MovieReviewsResponse
import com.example.cinemaspot.data.models.movies.watchList.WatchlistRequest
import com.example.cinemaspot.domain.usecase.AddToWatchlistUseCase
import com.example.cinemaspot.domain.usecase.GetMovieCastUseCase
import com.example.cinemaspot.domain.usecase.GetMovieDetailsUseCase
import com.example.cinemaspot.domain.usecase.GetMovieReviewsUseCase
import com.example.cinemaspot.domain.usecase.GetWatchListMoviesUseCase
import com.example.cinemaspot.domain.usecase.GetMovieTrailerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val getMovieWatchListUseCase: GetWatchListMoviesUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase
) :
    ViewModel() {
    private val _movieDetails = MutableStateFlow<MovieDetailsResponse?>(null)
    val movieDetails: StateFlow<MovieDetailsResponse?> get() = _movieDetails
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _reviews = MutableStateFlow<MovieReviewsResponse?>(null)
    val reviews: StateFlow<MovieReviewsResponse?> = _reviews
    private val _cast = MutableStateFlow<MovieCastResponse?>(null)
    val cast: StateFlow<MovieCastResponse?> = _cast
    private val _addToWatchlistStatus = MutableStateFlow<String?>(null)
    val addToWatchlistStatus: StateFlow<String?> = _addToWatchlistStatus
    private val _watchList = MutableStateFlow<List<Int>?>(null)
    val watchList: StateFlow<List<Int>?> = _watchList
    private val _trailerKey = MutableStateFlow<String?>(null)
    val trailerKey: StateFlow<String?> = _trailerKey
    private val _isLoadingAnotherPage = MutableStateFlow(false)
    val isLoadingAnotherPage: StateFlow<Boolean> = _isLoadingAnotherPage
    private var totalPage = 1
    private var currentPage = 1

    fun loadNextPage(movieId: Int) {
        if (!isLoading.value && currentPage < totalPage ) {
            currentPage++
            _isLoadingAnotherPage.value = true
            getMovieReviews(movieId,currentPage)
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val response = getMovieDetailsUseCase.getMovieDetails(movieId = movieId)
                if (response.isSuccessful) {
                    _movieDetails.value = response.body()!!
                    _isLoading.value = false
                } else {
                    Log.e(
                        "DetailsViewModel",
                        "Failed to fetch details: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching details", e)
            } finally {
                _isLoading.value = false
            }
        }

    }

    fun getMovieReviews(movieId: Int, page: Int =1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    getMovieReviewsUseCase.getMovieReviews(movieId = movieId, page = page)
                if (response.isSuccessful) {
                    _reviews.update { it -> response.body() }
                    Log.e("ReviewTap", "total page = $totalPage")
                } else {
                    Log.e(
                        "DetailsViewModel",
                        "Failed to fetch reviews: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching reviews", e)
            }finally {
                _isLoadingAnotherPage.value = false
            }
        }

    }

    fun getMovieCast(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getMovieCastUseCase.getMovieCast(movieId)
                if (response.isSuccessful) {
                    _cast.value = response.body()!!
                } else {
                    Log.e(
                        "DetailsViewModel",
                        "Failed to fetch credits: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching credits", e)
            }
        }

    }

    fun addMovieToWatchList(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    addToWatchlistUseCase.addMovie(movie = WatchlistRequest(mediaId = movieId))
                if (response.isSuccessful) {
                    _addToWatchlistStatus.value = "Success"
                    Log.e("AddingToWatchList", "You have successfully added this movie")
                } else {
                    _addToWatchlistStatus.value = "Failed"
                    Log.e(
                        "AddingToWatchList",
                        "Failed to fetch credits: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _addToWatchlistStatus.value = "Failed"
                Log.e("AddingToWatchList", "Error fetching credits", e)
            }
        }
    }

    fun getWatchListMovies(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getMovieWatchListUseCase.getMoviesListFromWatchList(page = page)
                if (response.isSuccessful) {
                    _watchList.value = response.body()!!.results.map { it.id }
                } else {
                    Log.e(
                        "DetailsViewModel",
                        "Failed to fetch credits: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("Fetching watchlist movies", "Error fetching credits", e)
            }
        }
    }

fun getMovieTrailer(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getMovieTrailerUseCase.getMovieTrailer(movieId)
                if (response.isSuccessful){
                    val videos = response.body()?.results?: emptyList()
                    Log.d("DetailsViewModel", "Fetched videos: $videos")
                    val trailer = videos.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }
                    Log.d("DetailsViewModel", "Selected Trailer: $trailer")
                    _trailerKey.value = trailer?.key
                }else{
                    Log.e("DetailsViewModel", "Failed to fetch trailer: ${response.errorBody()?.string()}")
                }

            }catch (e:Exception){
                Log.e("DetailsViewModel", "Error fetching trailer", e)
            }
        }
    }
}

