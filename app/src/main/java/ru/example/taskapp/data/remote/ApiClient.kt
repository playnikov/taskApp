package ru.example.taskapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.example.taskapp.data.remote.project.ProjectApiService
import ru.example.taskapp.data.remote.user.UserApiService

object ApiClient {
    private const val BASE_URL = "http://51.250.106.240:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)
    val projectApiService: ProjectApiService = retrofit.create(ProjectApiService::class.java)
    val taskApiService: TaskApiService = retrofit.create(TaskApiService::class.java)
    val userApiService: UserApiService = retrofit.create(UserApiService::class.java)
}