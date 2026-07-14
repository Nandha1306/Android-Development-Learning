package com.example.learnarchitecture.domain.repository


interface AuthRepository {
    fun login(
        email: String,
        password: String
    ): Boolean

}