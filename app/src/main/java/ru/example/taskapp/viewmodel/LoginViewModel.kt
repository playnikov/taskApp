package ru.example.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.example.taskapp.data.repository.AuthRepository

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val token: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginResult = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginResult: StateFlow<LoginState> = _loginResult

    fun login(username: String, password: String, navController: NavController) {
        viewModelScope.launch {
            _loginResult.value = LoginState.Loading
            authRepository.login(username, password)
                .onSuccess { token ->
                    _loginResult.value = LoginState.Success(token)
                    navController.navigate("main")
                }
                .onFailure { e ->
                    _loginResult.value = LoginState.Error(e.message ?: "Login failed")
                }
        }
    }

    suspend fun checkInitialAuthState(): Boolean {
        return authRepository.isLoggedIn()
    }
}