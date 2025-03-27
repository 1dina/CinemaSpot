package com.example.cinemaspot.ui.screens.home

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
import kotlinx.coroutines.flow.update
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
    private var topRatedPage = 1
    private var totalTopRatedPages = Int.MAX_VALUE
    private var isFetchingTopRated = false

    private val _nowPlayingMovies = MutableStateFlow<List<Result>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Result>> get() = _nowPlayingMovies
    private var nowPlayingPage = 1
    private var totalNowPlayingPages = Int.MAX_VALUE
    private var isFetchingNowPlaying = false

    private val _upcomingMovies = MutableStateFlow<List<Result>>(emptyList())
    val upcomingMovies: StateFlow<List<Result>> get() = _upcomingMovies
    private var upcomingPage = 1
    private var totalUpcomingPages = Int.MAX_VALUE
    private var isFetchingUpcoming = false

    private val _popularMovies = MutableStateFlow<List<Result>>(emptyList())
    val popularMovies: StateFlow<List<Result>> get() = _popularMovies
    private var popularPage = 1
    private var totalPopularPages = Int.MAX_VALUE
    private var isFetchingPopular = false

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _topFiveMovies = MutableStateFlow<List<Result>>(emptyList())
    val topFiveMovies: StateFlow<List<Result>> = _topFiveMovies

    fun fetchAllMovies() {
        fetchTopRatedMovies()
        fetchPopularMovies()
        fetchUpComingMovies()
        fetchNowPlayingMovies()
    }

    private fun fetchTopRatedMovies() {
        if (isFetchingTopRated || topRatedPage > totalTopRatedPages) return
        isFetchingTopRated = true
        if (topRatedPage == 1) _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getRecentMoviesUseCase.getRecentMovies(page = topRatedPage)
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _topRatedMovies.update { it + movies }
                    _topFiveMovies.value = getTopFiveMovies(_topRatedMovies.value)
                    totalTopRatedPages = response.body()?.total_pages ?: Int.MAX_VALUE
                    topRatedPage++
                } else {
                    Log.e(
                        "MovieViewModel",
                        "Failed to fetch movies: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies", e)
            } finally {
                if (topRatedPage == 2) _isLoading.value = false
                isFetchingTopRated = false
            }
        }
    }

    fun loadNextPage(category: String) {
        when (category) {
            "TopRated" -> fetchTopRatedMovies()
            "Popular" -> fetchPopularMovies()
            "Upcoming" -> fetchUpComingMovies()
            "NowPlaying" -> fetchNowPlayingMovies()
        }
    }

    private fun fetchPopularMovies() {
        if (isFetchingPopular || popularPage > totalPopularPages) return
        isFetchingPopular = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getPopularMoviesUseCase.getPopularMovies(page = popularPage)
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _popularMovies.update { it + movies }
                    totalPopularPages = response.body()?.total_pages ?: Int.MAX_VALUE
                    popularPage++
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching popular movies", e)
            } finally {
                isFetchingPopular = false
            }
        }
    }

    private fun fetchUpComingMovies() {
        if (isFetchingUpcoming || upcomingPage > totalUpcomingPages) return
        isFetchingUpcoming = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getUpComingMoviesUseCase.getUpComingMovies(page = upcomingPage)
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _upcomingMovies.update { it + movies }
                    totalUpcomingPages = response.body()?.total_pages ?: Int.MAX_VALUE
                    upcomingPage++
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching upcoming movies", e)
            } finally {
                isFetchingUpcoming = false
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        if (isFetchingNowPlaying || nowPlayingPage > totalNowPlayingPages) return
        isFetchingNowPlaying = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getNowPlayingMoviesUseCase.getNowPlayingMovies(page = nowPlayingPage)
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _nowPlayingMovies.update { it + movies }
                    totalNowPlayingPages = response.body()?.total_pages ?: Int.MAX_VALUE
                    nowPlayingPage++
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching now playing movies", e)
            } finally {
                isFetchingNowPlaying = false
            }
        }
    }

    private fun getTopFiveMovies(movies: List<Result>): List<Result> {
        return movies.take(5)
    }
}
