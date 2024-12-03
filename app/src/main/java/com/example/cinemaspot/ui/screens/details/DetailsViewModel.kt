package com.example.cinemaspot.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.models.movies.MovieDetailsResponse
import com.example.cinemaspot.data.models.movies.cast.MovieCastResponse
import com.example.cinemaspot.data.models.movies.reviews.MovieReviewsResponse
import com.example.cinemaspot.domain.usecase.GetMovieCastUseCase
import com.example.cinemaspot.domain.usecase.GetMovieDetailsUseCase
import com.example.cinemaspot.domain.usecase.GetMovieReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase
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

    fun getMovieReviews(movieId: Int , page : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getMovieReviewsUseCase.getMovieReviews(movieId = movieId,page = page)
                if (response.isSuccessful) {
                    _reviews.value = response.body()!!
                } else {
                    Log.e(
                        "DetailsViewModel",
                        "Failed to fetch reviews: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching reviews", e)
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
}