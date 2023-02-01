package com.ssafy.campinity.di

import com.ssafy.campinity.AuthInterceptorClient
import com.ssafy.campinity.NoAuthInterceptorClient
import com.ssafy.campinity.data.remote.service.AuthApiService
import com.ssafy.campinity.data.remote.service.NoteApiService
import com.ssafy.campinity.data.remote.service.CollectionApiService
import com.ssafy.campinity.data.remote.service.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthApiService(
        @NoAuthInterceptorClient retrofit: Retrofit
    ): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideNoteApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): NoteApiService =
        retrofit.create(NoteApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideCollectionApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): CollectionApiService =
        retrofit.create(CollectionApiService::class.java)
}