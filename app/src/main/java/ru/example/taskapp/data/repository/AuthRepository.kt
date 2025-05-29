package ru.example.taskapp.data.repository

import ru.example.taskapp.data.AppPreferences
import ru.example.taskapp.data.remote.ApiService
import ru.example.taskapp.data.remote.LoginRequest

class AuthRepository(
    private val apiService: ApiService,
    private val appPreferences: AppPreferences
)  {

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            response.data?.authToken?.let { token ->
                appPreferences.saveAuthToken(token)
                Result.success(token)
            } ?: Result.failure(Exception("Token not found in response"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isLoggedIn(): Boolean {
        return appPreferences.getAuthToken() != null
    }

    suspend fun logout() {
        appPreferences.clearAuthToken()
    }
}