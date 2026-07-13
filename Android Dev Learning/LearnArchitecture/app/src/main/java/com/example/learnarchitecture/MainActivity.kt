package com.example.learnarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.learnarchitecture.presentation.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

// Flow
// User clicks Login
//        │
//        ▼
//LoginScreen
//        │
//viewModel.login()
//        │
//        ▼
//LoginViewModel
//        │
//loginUseCase(email,password)
//        ▼
//LoginUseCase
//        │
//repository.login()
//        ▼
//AuthRepository Interface
//        │
//implemented by
//        ▼
//AuthRepositoryImpl
//        │
//Checks:
//email == admin@gmail.com
//password == admin123
//        │
//returns true/false
//        ▼
//UseCase
//        ▼
//ViewModel
//        ▼
//UiState updated
//        ▼
//Compose recomposes
//        ▼
//Message displayed