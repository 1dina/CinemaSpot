package com.example.cinemaspot.di

import com.example.cinemaspot.data.Constants
import com.example.cinemaspot.data.remote.ApiService
import com.example.cinemaspot.data.repository.AuthRepositoryImpl
import com.example.cinemaspot.domain.repository.AuthRepository
import com.example.cinemaspot.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        tmDbApi: ApiService,
    ): AuthRepository {
        val apiKey = Constants.API_KEY
        return AuthRepositoryImpl(tmDbApi, apiKey)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
}
