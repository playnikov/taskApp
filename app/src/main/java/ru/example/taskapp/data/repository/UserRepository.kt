package ru.example.taskapp.data.repository

import kotlinx.coroutines.flow.first
import okio.IOException
import ru.example.taskapp.data.AppPreferences
import ru.example.taskapp.data.model.User
import ru.example.taskapp.data.remote.user.UserApiService

class UserRepository(
    private val apiService: UserApiService,
    private val appPreferences: AppPreferences
) {
    suspend fun getUsers(): Result<List<User>> {
        return try {
            val authToken = appPreferences.getAuthToken().first()
                ?: return Result.failure(Exception("Not authenticated"))

            val response = apiService.getUsers("Bearer $authToken")

            if (response.isSuccessful) {
                Result.success(response.body()?.users ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}