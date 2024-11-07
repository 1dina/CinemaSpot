package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(val repo : MyRepository) {
     suspend fun getPopularMovies(page:Int) = repo.fetchPopularMovies(page)
}