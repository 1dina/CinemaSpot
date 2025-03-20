package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class GetWatchListMoviesUseCase @Inject constructor(private val repo: MyRepository) {
    suspend fun getMoviesListFromWatchList(page: Int) = repo.fetchMoviesFromWatchlist(page)
}