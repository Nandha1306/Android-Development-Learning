package com.example.learnarchitecture.di

import com.example.learnarchitecture.data.repository.AuthRepositoryImpl
import com.example.learnarchitecture.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//Whenever someone asks for: AuthRepository
//give them: AuthRepositoryImpl
@Module
@InstallIn(SingletonComponent::class)
abstract class RespositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}