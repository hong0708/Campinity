package com.ssafy.campinity.di

import com.ssafy.campinity.AuthInterceptorClient
import com.ssafy.campinity.NoAuthInterceptorClient
import com.ssafy.campinity.RefreshInterceptorClient
import com.ssafy.campinity.data.remote.service.*
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
    fun provideRefreshApiService(
        @RefreshInterceptorClient retrofit: Retrofit
    ): RefreshApiService =
        retrofit.create(RefreshApiService::class.java)

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

    @Provides
    @Singleton
    fun provideCurationApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): CurationApiService =
        retrofit.create(CurationApiService::class.java)

    @Provides
    @Singleton
    fun provideCommunityApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): CommunityApiService =
        retrofit.create(CommunityApiService::class.java)

    @Provides
    @Singleton
    fun provideHomeApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): HomeApiService =
        retrofit.create(HomeApiService::class.java)

    @Provides
    @Singleton
    fun provideMyPageApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): MyPageApiService =
        retrofit.create(MyPageApiService::class.java)

    @Provides
    @Singleton
    fun provideSearchApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): SearchApiService =
        retrofit.create(SearchApiService::class.java)

    @Provides
    @Singleton
    fun provideFCMApiService(
        @AuthInterceptorClient retrofit: Retrofit
    ): FCMApiService =
        retrofit.create(FCMApiService::class.java)
}