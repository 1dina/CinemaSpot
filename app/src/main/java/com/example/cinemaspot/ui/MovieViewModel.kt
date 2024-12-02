package com.example.cinemaspot.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.models.movies.Result
import com.example.cinemaspot.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.cinemaspot.domain.usecase.GetPopularMoviesUseCase
import com.example.cinemaspot.domain.usecase.GetRecentMoviesUseCase
import com.example.cinemaspot.domain.usecase.GetUpComingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getRecentMoviesUseCase: GetRecentMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase
) : ViewModel() {

    private val _topRatedMovies = MutableStateFlow<List<Result>>(emptyList())
    val topRatedMovies: StateFlow<List<Result>> get() = _topRatedMovies

    private val _nowPlayingMovies = MutableStateFlow<List<Result>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Result>> get() = _nowPlayingMovies

    private val _upcomingMovies = MutableStateFlow<List<Result>>(emptyList())
    val upcomingMovies: StateFlow<List<Result>> get() = _upcomingMovies

    private val _popularMovies = MutableStateFlow<List<Result>>(emptyList())
    val popularMovies: StateFlow<List<Result>> get() = _popularMovies

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _topFiveMovies = MutableStateFlow<List<Result>>(emptyList())
    val topFiveMovies: StateFlow<List<Result>> = _topFiveMovies

    fun fetchAllMovies() {
        fetchTopRatedMovies(1)
        fetchPopularMovies(1)
        fetchUpComingMovies(1)
        fetchNowPlayingMovies(1)
    }


    private fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val response = getRecentMoviesUseCase.getRecentMovies(page = page)
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _topRatedMovies.value = movies
                    _topFiveMovies.value = getTopFiveMovies(movies)
                    Log.d("MovieViewModel", "Movies: ${response.body()?.results}")
                } else {
                    Log.e(
                        "MovieViewModel",
                        "Failed to fetch movies: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getTopFiveMovies(movies: List<Result>): List<Result> {
        return movies.take(5)
    }

    private fun fetchNowPlayingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getNowPlayingMoviesUseCase.getNowPlayingMovies(page = page)
                if (response.isSuccessful) {
                    _nowPlayingMovies.value = response.body()?.results ?: emptyList()
                    Log.d("MovieViewModel2", "Movies: ${response.body()?.results}")
                } else {
                    Log.e(
                        "MovieViewModel2",
                        "Failed to fetch movies: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel2", "Error fetching movies", e)
            }
        }
    }

    private fun fetchPopularMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getPopularMoviesUseCase.getPopularMovies(page = page)
                if (response.isSuccessful) {
                    _popularMovies.value = response.body()?.results ?: emptyList()
                    Log.d("MovieViewModel3", "Movies: ${response.body()?.results}")
                } else {
                    Log.e(
                        "MovieViewModel3",
                        "Failed to fetch movies: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel3", "Error fetching movies", e)
            }
        }
    }

    private fun fetchUpComingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getUpComingMoviesUseCase.getUpComingMovies(page = page)
                if (response.isSuccessful) {
                    _upcomingMovies.value = response.body()?.results ?: emptyList()
                    Log.d("MovieViewModel4", "Movies: ${response.body()?.results}")
                } else {
                    Log.e(
                        "MovieViewModel4",
                        "Failed to fetch movies: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel4", "Error fetching movies :", e)
            }
        }
    }
}