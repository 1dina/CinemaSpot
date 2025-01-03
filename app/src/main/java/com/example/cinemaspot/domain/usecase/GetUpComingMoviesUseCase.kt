package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.MyRepository
import javax.inject.Inject


class GetUpComingMoviesUseCase @Inject constructor(val repo : MyRepository) {
    suspend fun getUpComingMovies (page : Int) = repo.fetchUpComingMovies(page)
}