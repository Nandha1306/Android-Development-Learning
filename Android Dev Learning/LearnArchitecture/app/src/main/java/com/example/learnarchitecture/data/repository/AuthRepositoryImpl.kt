package com.example.learnarchitecture.data.repository

import com.example.learnarchitecture.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override fun login(
        email: String,
        password: String
    ): Boolean {
        return email == "admin@gmail.com" &&
                password == "admin123"

    }
}