package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(val repo: MyRepository) {
    suspend fun getMovieDetails(movieId: Int) = repo.fetchNowPlayingMovies(movieId)

}