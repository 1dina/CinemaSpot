package com.example.cinemaspot.ui.screens.wishList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.models.movies.watchList.Result
import com.example.cinemaspot.domain.usecase.GetWatchListMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val getWatchListMoviesUseCase: GetWatchListMoviesUseCase
) : ViewModel() {

    private val _watchList = MutableStateFlow<List<Result>>(emptyList())
    val watchList: StateFlow<List<Result>> = _watchList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadingAnotherPage = MutableStateFlow(false)
    val isLoadingAnotherPage: StateFlow<Boolean> = _isLoadingAnotherPage

    private var totalPage = 1
    private var currentPage = 1
    private var isFirstTime = true

    init {
        viewModelScope.launch {
            WatchlistSyncCenter.events.collect { event ->
                when (event) {
                    is WatchlistEvent.MovieRemoved -> {
                        _watchList.update { current ->
                            current.filterNot { it.id == event.movieId }
                        }
                    }

                    is WatchlistEvent.MovieAdded -> {
                        getWatchListMovies(resetList = true)
                    }
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLoading.value && currentPage > 1) {
            currentPage--
            _isLoadingAnotherPage.value = true
            getWatchListMovies(currentPage)
        }
    }

    fun getWatchListMovies(page: Int = 1, resetList: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getWatchListMoviesUseCase.getMoviesListFromWatchList(page = page)
                if (response.isSuccessful) {
                    if (isFirstTime) {
                        totalPage = response.body()!!.total_pages
                        currentPage = totalPage
                        isFirstTime = false
                    }

                    if (resetList || page == totalPage) {
                        _watchList.value = emptyList()
                    }

                    _watchList.update { current ->
                        current + response.body()!!.results.reversed()
                    }

                    Log.d("Watchlist", "Current list size: ${_watchList.value.size}")

                } else {
                    Log.e(
                        "Fetching watchlist movies",
                        "Failed to fetch credits: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("Fetching watchlist movies", "Error fetching credits", e)
            } finally {
                _isLoading.value = false
                _isLoadingAnotherPage.value = false
            }
        }
    }
}

object WatchlistSyncCenter {
    private val _events = MutableSharedFlow<WatchlistEvent>(replay = 0)
    val events: SharedFlow<WatchlistEvent> = _events

    suspend fun emit(event: WatchlistEvent) {
        _events.emit(event)
    }
}

sealed class WatchlistEvent {
    data class MovieRemoved(val movieId: Int) : WatchlistEvent()
    data class MovieAdded(val movieId: Int) : WatchlistEvent()
}
