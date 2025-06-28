package com.example.kkp.api

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "KKPSession"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    
    private var sharedPreferences: SharedPreferences? = null
    
    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }
    
    fun saveAuthToken(token: String) {
        sharedPreferences?.edit()?.putString(KEY_AUTH_TOKEN, token)?.apply()
    }
    
    fun getAuthToken(): String? {
        return sharedPreferences?.getString(KEY_AUTH_TOKEN, null)
    }
    
    fun clearAuthToken() {
        sharedPreferences?.edit()?.remove(KEY_AUTH_TOKEN)?.apply()
    }
    
    fun saveUserInfo(userId: Long, userName: String, userEmail: String) {
        sharedPreferences?.edit()
            ?.putLong(KEY_USER_ID, userId)
            ?.putString(KEY_USER_NAME, userName)
            ?.putString(KEY_USER_EMAIL, userEmail)
            ?.apply()
    }
    
    fun getUserInfo(): Triple<Long, String, String>? {
        val userId = sharedPreferences?.getLong(KEY_USER_ID, -1) ?: -1
        val userName = sharedPreferences?.getString(KEY_USER_NAME, "") ?: ""
        val userEmail = sharedPreferences?.getString(KEY_USER_EMAIL, "") ?: ""
        
        return if (userId != -1L && userName.isNotEmpty() && userEmail.isNotEmpty()) {
            Triple(userId, userName, userEmail)
        } else {
            null
        }
    }
    
    fun clearUserInfo() {
        sharedPreferences?.edit()
            ?.remove(KEY_USER_ID)
            ?.remove(KEY_USER_NAME)
            ?.remove(KEY_USER_EMAIL)
            ?.apply()
    }
    
    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }
    
    fun logout() {
        clearAuthToken()
        clearUserInfo()
    }
} 