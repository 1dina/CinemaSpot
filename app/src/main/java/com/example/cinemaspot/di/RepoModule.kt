package com.example.cinemaspot.di

import com.example.cinemaspot.data.repository.MyRepositoryImpl
import com.example.cinemaspot.domain.repository.MyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindRepository(repoImpl: MyRepositoryImpl): MyRepository

}