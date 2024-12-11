package com.example.cinemaspot.di

import android.app.Application
import android.content.Context
import com.example.cinemaspot.data.local.EncryptedPrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideEncryptedPrefsManager(context: Context): EncryptedPrefsManager {
        return EncryptedPrefsManager(context)
    }
}