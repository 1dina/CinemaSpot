package com.example.cinemaspot.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.cinemaspot.domain.usecase.GetPopularMoviesUseCase
import com.example.cinemaspot.domain.usecase.GetRecentMoviesUseCase
import com.example.cinemaspot.domain.usecase.GetUpComingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getRecentMoviesUseCase: GetRecentMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase
) : ViewModel() {

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = getRecentMoviesUseCase.getRecentMovies(page = page)
                if (response.isSuccessful) {
                    Log.d("MovieViewModel", "Movies: ${response.body()?.results}")
                } else {
                    Log.e("MovieViewModel", "Failed to fetch movies: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies", e)
            }
        }
    }

    fun fetchNowPlayingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getNowPlayingMoviesUseCase.getNowPlayingMovies(page = page)
                if (response.isSuccessful) {
                    Log.d("MovieViewModel2", "Movies: ${response.body()?.results}")
                } else {
                    Log.e("MovieViewModel2", "Failed to fetch movies: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel2", "Error fetching movies", e)
            }
        }
    }

    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getPopularMoviesUseCase.getPopularMovies(page = page)
                if (response.isSuccessful) {
                    Log.d("MovieViewModel3", "Movies: ${response.body()?.results}")
                } else {
                    Log.e("MovieViewModel3", "Failed to fetch movies: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel3", "Error fetching movies", e)
            }
        }
    }

    fun fetchUpComingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getUpComingMoviesUseCase.getUpComingMovies(page = page)
                if (response.isSuccessful) {
                    Log.d("MovieViewModel4", "Movies: ${response.body()?.results}")
                } else {
                    Log.e("MovieViewModel4", "Failed to fetch movies: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel4", "Error fetching movies", e)
            }
        }
    }
}