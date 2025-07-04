package com.example.kkp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kkp.model.LoginResponse
import com.example.kkp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    
    private val authRepository = AuthRepository()
    
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    
    private val _isLoggedIn = MutableStateFlow(authRepository.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    private val _userInfo = MutableStateFlow<Triple<Long, String, String>?>(authRepository.getUserInfo())
    val userInfo: StateFlow<Triple<Long, String, String>?> = _userInfo.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            val result = authRepository.login(email, password)
            result.fold(
                onSuccess = { response ->
                    _loginState.value = LoginState.Success(response)
                    _isLoggedIn.value = true
                    _userInfo.value = authRepository.getUserInfo()
                },
                onFailure = { exception ->
                    _loginState.value = LoginState.Error(exception.message ?: "Login failed")
                }
            )
        }
    }
    
    fun logout() {
        authRepository.logout()
        _isLoggedIn.value = false
        _userInfo.value = null
        _loginState.value = LoginState.Idle
    }
    
    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
} 