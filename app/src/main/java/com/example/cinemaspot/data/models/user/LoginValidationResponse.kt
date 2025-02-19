package com.example.cinemaspot.data.models.user

data class LoginValidationResponse(
    val success: Boolean,
    val request_token: String
)
