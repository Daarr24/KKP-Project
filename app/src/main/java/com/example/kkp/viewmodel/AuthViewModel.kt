package com.example.kkp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kkp.model.LoginResponse
import com.example.kkp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.kkp.model.DashboardData
import com.example.kkp.model.DashboardResponse
import com.example.kkp.api.NetworkModule

class AuthViewModel : ViewModel() {
    
    private val authRepository = AuthRepository()
    
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    
    private val _isLoggedIn = MutableStateFlow(authRepository.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    private val _userInfo = MutableStateFlow<Triple<Long, String, String>?>(authRepository.getUserInfo())
    val userInfo: StateFlow<Triple<Long, String, String>?> = _userInfo.asStateFlow()
    
    // Tambahan untuk dashboard
    private val _dashboardData = MutableStateFlow<DashboardData?>(null)
    val dashboardData: StateFlow<DashboardData?> = _dashboardData.asStateFlow()
    private val _dashboardLoading = MutableStateFlow(false)
    val dashboardLoading: StateFlow<Boolean> = _dashboardLoading.asStateFlow()
    private val _dashboardError = MutableStateFlow<String?>(null)
    val dashboardError: StateFlow<String?> = _dashboardError.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            val result = authRepository.login(email, password)
            result.fold(
                onSuccess = { response ->
                    _isLoggedIn.value = true
                    _loginState.value = LoginState.Success(response)
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

    fun fetchDashboard() {
        viewModelScope.launch {
            _dashboardLoading.value = true
            _dashboardError.value = null
            try {
                val response = NetworkModule.apiService.getDashboard()
                if (response.isSuccessful && response.body() != null) {
                    _dashboardData.value = response.body()!!.data
                } else {
                    _dashboardError.value = response.body()?.message ?: "Failed to fetch dashboard"
                }
            } catch (e: Exception) {
                _dashboardError.value = e.message
            } finally {
                _dashboardLoading.value = false
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
} 