package com.example.learnarchitecture.domain.usecase

import com.example.learnarchitecture.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String
    ): Boolean {
        return repository.login(email, password)
    }
}