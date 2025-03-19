package com.example.cinemaspot.ui.screens.wishList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.models.movies.watchList.Result
import com.example.cinemaspot.domain.usecase.GetWatchListMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val getWatchListMoviesUseCase: GetWatchListMoviesUseCase) : ViewModel() {
    private val _watchList = MutableStateFlow<List<Result>>(emptyList())
    val watchList: StateFlow<List<Result>> = _watchList
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getWatchListMovies(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getWatchListMoviesUseCase.getMoviesListFromWatchList(page = page)
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _watchList.value = response.body()!!.results
                } else {
                    _isLoading.value = false
                    Log.e(
                        "Fetching watchlist movies",
                        "Failed to fetch credits: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e("Fetching watchlist movies", "Error fetching credits", e)
            }
        }
    }
}