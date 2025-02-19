package com.example.cinemaspot.domain.usecase

import com.example.cinemaspot.domain.repository.AuthRepository
import com.example.cinemaspot.ui.screens.auth.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend fun execute(username: String, password: String): Result<String> {
        // الحصول على Request Token
        val requestTokenResult = authRepository.getRequestToken()
        return when (requestTokenResult) {
            is Result.Success -> {
                val loginRequest =
                    authRepository.validateLogin(requestTokenResult.data, username, password)
                when (loginRequest) {
                    is Result.Success -> {
                        val sessionResult = authRepository.createSession(requestTokenResult.data)
                        when (sessionResult) {
                            is Result.Success -> {
                                Result.Success(sessionResult.data)
                            }

                            is Result.Error -> {
                                Result.Error(Exception("Failed to create session"))
                            }
                        }
                    }

                    is Result.Error -> {
                        Result.Error(Exception("Login failed"))
                    }
                }
            }

            is Result.Error -> {
                Result.Error(Exception("Failed to get request token"))
            }

        }
    }
}
