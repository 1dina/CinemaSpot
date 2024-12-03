package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class GetMovieCastUseCase @Inject constructor(private val repo: MyRepository) {
    suspend fun getMovieCast(movieId: Int) = repo.fetchMovieCasts(movieId)
}