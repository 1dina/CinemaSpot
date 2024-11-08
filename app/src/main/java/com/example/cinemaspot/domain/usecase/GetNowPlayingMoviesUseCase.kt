package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(val repo: MyRepository) {
    suspend fun getNowPlayingMovies(page: Int) = repo.fetchNowPlayingMovies(page)
}