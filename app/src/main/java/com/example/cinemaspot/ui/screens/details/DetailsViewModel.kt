package com.example.cinemaspot.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {

     fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getMovieDetailsUseCase.getMovieDetails(movieId = movieId)
                if (response.isSuccessful) {
                    Log.d("DetailsViewModel", "Details: ${response.body()?.results}")
                } else {
                    Log.e(
                        "DetailsViewModel",
                        "Failed to fetch details: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching details", e)
            }
        }

    }
}