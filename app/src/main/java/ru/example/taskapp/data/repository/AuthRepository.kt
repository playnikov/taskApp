package ru.example.taskapp.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okio.IOException
import ru.example.taskapp.data.AppPreferences
import ru.example.taskapp.data.remote.AuthApiService
import ru.example.taskapp.data.remote.LoginRequest

class AuthRepository(
    private val authApiService: AuthApiService,
    private val appPreferences: AppPreferences
)  {

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = authApiService.login(LoginRequest(username, password))
            response.data?.let { data ->
                appPreferences.saveAuthToken(data.authToken.toString())
                appPreferences.saveRole(data.user?.type ?: "")
                Result.success(data.authToken.toString())
            } ?: Result.failure(Exception("Token not found in response"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun isLoggedIn(): Boolean {
        val authToken: String? = appPreferences.getAuthToken().first()

        return try {
            val response = runBlocking { authApiService.validateToken("Bearer $authToken") }
            response.isSuccessful
        } catch (e: IOException) {
            appPreferences.clearAuthToken()
            false
        } catch (e: Exception) {
            appPreferences.clearAuthToken()
            false
        }
    }

    suspend fun logout() {
        appPreferences.clearAuthToken()
    }
}