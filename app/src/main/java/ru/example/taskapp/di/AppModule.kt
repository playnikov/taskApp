package ru.example.taskapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.example.taskapp.data.AppPreferences
import ru.example.taskapp.data.remote.ApiClient
import ru.example.taskapp.data.repository.AuthRepository
import ru.example.taskapp.viewmodel.LoginViewModel


val appModule = module {
    single { ApiClient.apiService }
    single { AppPreferences(get()) }
    single{ AuthRepository(get(), get()) }
    viewModel { LoginViewModel(get()) }
}