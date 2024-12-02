package com.example.cinemaspot.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.models.movies.MovieDetailsResponse
import com.example.cinemaspot.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {
    private val _movieDetails = MutableStateFlow<MovieDetailsResponse?>(null)
    val movieDetails: StateFlow<MovieDetailsResponse?> get() = _movieDetails
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

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
}