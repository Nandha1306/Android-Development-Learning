package com.example.learnarchitecture.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.learnarchitecture.data.repository.AuthRepositoryImpl
import com.example.learnarchitecture.domain.usecase.LoginUseCase

class LoginViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl()
    private val loginUseCase = LoginUseCase(repository)
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun login() {
//        println("Email: '${uiState.email}'")
//        println("Password: '${uiState.password}'")

        val success = loginUseCase(uiState.email, uiState.password)

//        println("Success: $success")

        uiState = uiState.copy(
            message = if (success) "Login Successful"
            else "Invalid Credentials"
        )
    }

}