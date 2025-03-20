package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.data.models.movies.watchList.WatchlistRequest
import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(private val repo: MyRepository) {
    suspend fun addMovie (movie : WatchlistRequest) = repo.insertMovieToWatchList(movie)
}