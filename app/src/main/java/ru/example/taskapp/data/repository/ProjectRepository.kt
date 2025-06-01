package ru.example.taskapp.data.repository

import kotlinx.coroutines.flow.first
import okio.IOException
import retrofit2.Response
import ru.example.taskapp.data.AppPreferences
import ru.example.taskapp.data.model.Project
import ru.example.taskapp.data.remote.project.ProjectApiService
import ru.example.taskapp.data.remote.project.ProjectResponse
import ru.example.taskapp.data.remote.project.ProjectsResponse

class ProjectRepository(
    private val projectApiService: ProjectApiService,
    private val appPreferences: AppPreferences
) {
    suspend fun getProjects(): Result<List<Project>> {
        return try {
            val authToken = appPreferences.getAuthToken().first()
                ?: return Result.failure(Exception("Not authenticated"))

            val userRole = appPreferences.getUserRole().first() ?: return Result.failure(Exception("User role not found"))

            when(userRole) {
                "admin" -> getAllProjects(authToken)
                else -> getMyProjects(authToken)
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getAllProjects(authToken: String): Result<List<Project>> {
        val response = projectApiService.getAllProjects("Bearer $authToken")
        return processProjectResponse(response)
    }

    private suspend fun getMyProjects(authToken: String): Result<List<Project>> {
        val response = projectApiService.getMyProjects("Bearer $authToken")
        return processProjectResponse(response)
    }

    suspend fun getProjectById(projectId: Int): Result<Project> {
        return try {
            val authToken = appPreferences.getAuthToken().first()
                ?: return Result.failure(Exception("Not authenticated"))

            val response = projectApiService.getProjectById("Bearer $authToken", projectId) // Pass projectId as argument

            if (response.isSuccessful) {
                response.body()?.let { projectResponse ->
                    val project = Project(
                        id = projectResponse.project.id,
                        title = projectResponse.project.title,
                        description = projectResponse.project.description,
                        ownerId = projectResponse.project.ownerId,
                        ownerName = projectResponse.project.ownerName,
                        createdAt = projectResponse.project.createdAt
                    )
                    Result.success(project)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    private fun processProjectResponse(response: Response<ProjectsResponse>): Result<List<Project>> {
        return if (response.isSuccessful) {
            Result.success(response.body()?.projects ?: emptyList())
        } else {
            Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
        }
    }


    suspend fun getUserRole(): String? {
        return appPreferences.getUserRole().first()
    }
}