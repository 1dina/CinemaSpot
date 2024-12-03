package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(private val repo: MyRepository) {
    suspend fun getMovieReviews(movieId: Int, page: Int) =
        repo.fetchMovieReviews(movieId = movieId, page = page)

}