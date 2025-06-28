package com.example.kkp.repository

import com.example.kkp.api.NetworkModule
import com.example.kkp.api.SessionManager
import com.example.kkp.model.LoginRequest
import com.example.kkp.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = NetworkModule.apiService.login(loginRequest)
                
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {
                        // Save auth token and user info
                        loginResponse.token?.let { SessionManager.saveAuthToken(it) }
                        loginResponse.user?.let { user ->
                            SessionManager.saveUserInfo(user.id, user.name, user.email)
                        }
                        Result.success(loginResponse)
                    } else {
                        Result.failure(Exception(loginResponse?.message ?: "Login failed"))
                    }
                } else {
                    Result.failure(Exception("Login failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    fun logout() {
        SessionManager.logout()
    }
    
    fun isLoggedIn(): Boolean {
        return SessionManager.isLoggedIn()
    }
    
    fun getUserInfo(): Triple<Long, String, String>? {
        return SessionManager.getUserInfo()
    }
} 