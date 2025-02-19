package com.example.cinemaspot.data.repository

import com.example.cinemaspot.data.models.user.LoginRequest
import com.example.cinemaspot.data.remote.ApiService
import com.example.cinemaspot.domain.repository.AuthRepository
import com.example.cinemaspot.ui.screens.auth.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tmDbApi: ApiService,
    private val apiKey: String
) : AuthRepository {

    override suspend fun getRequestToken(): Result<String> {
        return try {
            val response = tmDbApi.getRequestToken(apiKey)
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.success) {
                        Result.Success(it.request_token)
                    } else {
                        Result.Error(Exception("Failed to get request token"))
                    }
                } ?: Result.Error(Exception("Empty response body"))
            } else {
                Result.Error(Exception("API request failed with code ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun validateLogin(
        requestToken: String,
        username: String,
        password: String
    ): Result<String> {
        return try {
            val loginRequest = LoginRequest(username, password, requestToken)
            val response = tmDbApi.validateLogin(apiKey, loginRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.success) {
                        Result.Success(it.request_token)
                    } else {
                        Result.Error(Exception("Login failed"))
                    }
                } ?: Result.Error(Exception("Empty response body"))
            } else {
                Result.Error(Exception("Login failed with code ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun createSession(requestToken: String): Result<String> {
        return try {
            val response = tmDbApi.createSession(apiKey, requestToken)
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.success) {
                        Result.Success(it.session_id)
                    } else {
                        Result.Error(Exception("Failed to create session"))
                    }
                } ?: Result.Error(Exception("Empty response body"))
            } else {
                Result.Error(Exception("Session creation failed with code ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
