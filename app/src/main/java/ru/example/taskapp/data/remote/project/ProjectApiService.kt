package ru.example.taskapp.data.remote.project

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.example.taskapp.data.model.Project
import ru.example.taskapp.data.model.ProjectParticipant
import ru.example.taskapp.data.remote.project.params.CreateProjectParams
import ru.example.taskapp.data.remote.project.params.ParticipantParams
import ru.example.taskapp.data.remote.project.params.UpdateProjectParams

interface ProjectApiService {
    @GET("/project")
    suspend fun getMyProjects(
        @Header("Authorization") token: String
    ): Response<ProjectsResponse>

    @POST("/project/create")
    suspend fun createProject(
        @Header("Authorization") token: String,
        @Body createProjectParams: CreateProjectParams
    ): Response<ProjectResponse>

    @PUT("/project/update")
    suspend fun updateProject(
        @Header("Authorization") token: String,
        @Body updateProjectParams: UpdateProjectParams
    ): Response<String>

    @DELETE("/project/delete/{id}")
    suspend fun deleteProject(
        @Header("Authorization") token: String,
        @Path("id") projectId: Int
    ): Response<String>

    @GET("/project/owner/{id}")
    suspend fun getProjectsByOwner(
        @Header("Authorization") token: String,
        @Path("id") ownerId: Int
    ): Response<ProjectsResponse>

    @POST("/project/addParticipant")
    suspend fun addParticipant(
        @Header("Authorization") token: String,
        @Body params: ParticipantParams
    ): Response<Unit>

    @GET("/project/{id}")
    suspend fun getProjectById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ProjectResponse>

    @GET("/project/allProjects")
    suspend fun getAllProjects(
        @Header("Authorization") token: String
    ): Response<ProjectsResponse>

    @DELETE("/project/removeParticipant")
    suspend fun removeParticipant(
        @Header("Authorization") token: String,
        @Body params: ParticipantParams
    ): Response<Unit>

    @GET("/project/participant/{projectId}")
    suspend fun getProjectParticipants(
        @Header("Authorization") token: String,
        @Path("projectId") projectId: Int
    ): Response<ParticipantsResponse>

//    @GET("/project/chat")
//    suspend fun getProjectChat(
//        @Header("Authorization") token: String,
//        @Query("projectId") projectId: Int
//    ): Response<ChatResponse>
}

data class ProjectsResponse(
    @SerializedName("data")
    val projects: List<Project>
)

data class ProjectResponse(
    @SerializedName("data")
    val project: Project
)

data class ParticipantsResponse(
    @SerializedName("data")
    val participants: List<ProjectParticipant>
)