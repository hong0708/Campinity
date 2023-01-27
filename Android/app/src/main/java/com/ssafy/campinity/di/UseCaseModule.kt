package com.ssafy.campinity.di

import com.ssafy.campinity.domain.repository.AuthRepository
import com.ssafy.campinity.domain.repository.UserRepository
import com.ssafy.campinity.domain.usecase.auth.LoginUseCase
import com.ssafy.campinity.domain.usecase.user.EditUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideEditUserUseCase(userRepository: UserRepository): EditUserUseCase {
        return EditUserUseCase(userRepository)
    }
}