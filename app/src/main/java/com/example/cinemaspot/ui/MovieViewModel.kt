package com.example.cinemaspot.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.remote.ApiService
import com.example.cinemaspot.domain.usecase.GetRecentMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCase: GetRecentMoviesUseCase
) : ViewModel() {

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch {
            try {
                val response = useCase.getRecentMovies(page = page)
                if (response.isSuccessful) {
                    // Log the successful response
                    Log.d("MovieViewModel", "Movies: ${response.body()?.results}")
                } else {
                    Log.e("MovieViewModel", "Failed to fetch movies: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies", e)
            }
        }
    }
}