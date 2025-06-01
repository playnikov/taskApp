package ru.example.taskapp.data.repository

import kotlinx.coroutines.flow.first
import okio.IOException
import ru.example.taskapp.data.AppPreferences
import ru.example.taskapp.data.model.Task
import ru.example.taskapp.data.remote.TaskApiService

class TaskRepository(
    private val apiService: TaskApiService,
    private val appPreferences: AppPreferences
) {
    suspend fun getMyTasks(): Result<List<Task>> {
        return try {
            val authToken = appPreferences.getAuthToken().first()
                ?: return Result.failure(Exception("Not authenticated"))

            val response = apiService.getMyTasks("Bearer $authToken")

            if (response.isSuccessful) {
                Result.success(response.body()?.tasks ?: emptyList())
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