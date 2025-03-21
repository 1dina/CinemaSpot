package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class SearchForMovieUseCase @Inject constructor(val repo: MyRepository) {
    suspend fun searchMovie(query: String, page: Int) = repo.searchForMovie(query, page)
}