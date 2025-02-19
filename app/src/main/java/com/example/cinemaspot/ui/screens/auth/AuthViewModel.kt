package com.example.cinemaspot.ui.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaspot.data.local.EncryptedPrefsManager
import com.example.cinemaspot.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase, private val prefsManager: EncryptedPrefsManager
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> get() = _usernameError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError


    fun loginUser(username: String, password: String, onLoginSuccess: () -> Unit) {
        if (!validateUsernameAndPassword(username, password)) return

        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = loginUseCase.execute(username, password)
            _isLoading.postValue(false)
            when (result) {
                is Result.Success -> {
                    prefsManager.saveSessionId(result.data)
                    onLoginSuccess()
                }

                is Result.Error -> {
                    _error.postValue(
                        result.exception.message ?: "Login failed"
                    )
                }

            }
        }
    }

    private fun validateUsernameAndPassword(username: String, password: String): Boolean {
        if (username.isBlank()) {
            _usernameError.postValue("Username is required")
            return false
        } else {
            _usernameError.postValue(null)
        }
        if (password.isBlank()) {
            _passwordError.postValue("Password is required")
            _passwordError.value = null
            return false
        } else if (password.length < 6 || !password.any { it.isDigit() }) {
            _passwordError.postValue("Password must be at least 6 characters long and contain at least one number")
            _passwordError.value = null
            return false
        } else {
            _passwordError.postValue(null)
        }
        return true
    }
}