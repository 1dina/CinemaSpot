package com.example.cinemaspot.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.models.movies.Result
import com.example.cinemaspot.domain.usecase.SearchForMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val searchForMovieUseCase: SearchForMovieUseCase
) : ViewModel() {
    private val _resultList = MutableStateFlow<List<Result>>(emptyList())
    val resultList: StateFlow<List<Result>> = _resultList
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun searchForMovie(query: String, page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val response = searchForMovieUseCase.searchMovie(query, page)
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _resultList.value = response.body()?.results!!
                } else {
                    _isLoading.value = false
                    Log.e(
                        "Search for movie",
                        "Failed to fetch credits: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e(
                    "Search for movie",
                    "Error Message: ${e.message}"
                )
            }
        }
    }
}