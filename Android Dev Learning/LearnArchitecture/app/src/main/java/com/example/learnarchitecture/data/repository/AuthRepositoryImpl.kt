package com.example.learnarchitecture.data.repository

import com.example.learnarchitecture.domain.repository.AuthRepository
import jakarta.inject.Inject

//@Inject constructor() -> No manual creation anymore.
class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override fun login(
        email: String,
        password: String
    ): Boolean {

        return email == "admin@gmail.com" &&
                password == "admin123"

    }
}