package ru.example.taskapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.example.taskapp.data.AppPreferences
import ru.example.taskapp.data.remote.ApiClient
import ru.example.taskapp.data.repository.AuthRepository
import ru.example.taskapp.data.repository.ProjectRepository
import ru.example.taskapp.data.repository.TaskRepository
import ru.example.taskapp.data.repository.UserRepository
import ru.example.taskapp.viewmodel.LoginViewModel
import ru.example.taskapp.viewmodel.ProjectViewModel
import ru.example.taskapp.viewmodel.TaskViewModel


val appModule = module {
    single { ApiClient.authApiService }
    single { ApiClient.projectApiService }
    single { ApiClient.taskApiService }
    single { ApiClient.userApiService }
    single { AppPreferences(get()) }
    single{ AuthRepository(get(), get()) }
    single { ProjectRepository(get(), get()) }
    single { TaskRepository(get(), get()) }
    single { UserRepository(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProjectViewModel(get(), get()) }
    viewModel { TaskViewModel(get()) }

}