package com.example.cinemaspot.domain.repository

import com.example.cinemaspot.ui.screens.auth.Result

interface AuthRepository {

    suspend fun getRequestToken(): Result<String>

    suspend fun validateLogin(
        requestToken: String,
        username: String,
        password: String
    ): Result<String>

    suspend fun createSession(requestToken: String): Result<String>
}
