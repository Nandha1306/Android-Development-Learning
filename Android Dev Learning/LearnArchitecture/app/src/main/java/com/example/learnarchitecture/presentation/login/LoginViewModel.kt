package com.example.learnarchitecture.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.learnarchitecture.data.repository.AuthRepositoryImpl
import com.example.learnarchitecture.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import androidx.hilt.navigation.compose.hiltViewModel

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
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

        val success = loginUseCase(
            uiState.email,
            uiState.password
        )

//        println("Success: $success")

        uiState = uiState.copy(
            message = if (success)
                "Login Successful"
            else
                "Invalid Credentials"
        )
    }
}