package com.example.cinemaspot.data.models.user

data class LoginRequest(
    val username: String,
    val password: String,
    val request_token: String
)