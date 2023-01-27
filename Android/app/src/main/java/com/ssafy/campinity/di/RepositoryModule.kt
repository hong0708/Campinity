package com.ssafy.campinity.di

import com.ssafy.campinity.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.campinity.data.remote.datasource.user.UserRemoteDataSourceImpl
import com.ssafy.campinity.data.remote.repository.AuthRepositoryImpl
import com.ssafy.campinity.data.remote.repository.UserRepositoryImpl
import com.ssafy.campinity.domain.repository.AuthRepository
import com.ssafy.campinity.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSourceImpl)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSourceImpl)
    }
}
