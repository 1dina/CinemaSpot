package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class GetRecentMoviesUseCase @Inject constructor(private val repo : MyRepository) {
   suspend fun getRecentMovies(page : Int) = repo.fetchTopRatedMovies(page)
}