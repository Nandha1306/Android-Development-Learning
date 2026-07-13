package com.example.learnarchitecture.presentation.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val message: String = ""
)