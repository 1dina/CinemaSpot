package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(private val repo: MyRepository) {
    suspend fun getMovieTrailer(movieId: Int) = repo.getMovieTrailer(movieId)
}