package com.ssafy.campinity.di

import com.ssafy.campinity.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.campinity.data.remote.datasource.collection.CollectionRemoteDataSourceImpl
import com.ssafy.campinity.data.remote.datasource.curation.CurationRemoteDataSourceImpl
import com.ssafy.campinity.data.remote.datasource.home.HomeRemoteDataSourceImpl
import com.ssafy.campinity.data.remote.datasource.note.NoteRemoteDataSourceImpl
import com.ssafy.campinity.data.remote.datasource.user.UserRemoteDataSourceImpl
import com.ssafy.campinity.data.remote.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideAuthDataSource(
        authApiService: AuthApiService
    ): AuthRemoteDataSourceImpl = AuthRemoteDataSourceImpl(authApiService)

    @Provides
    @Singleton
    fun provideUserDataSource(
        userApiService: UserApiService
    ): UserRemoteDataSourceImpl = UserRemoteDataSourceImpl(userApiService)

    @Provides
    @Singleton
    fun provideNoteDataSource(
        noteApiService: NoteApiService
    ): NoteRemoteDataSourceImpl = NoteRemoteDataSourceImpl(noteApiService)

    @Provides
    @Singleton
    fun provideCollectionDataSource(
        collectionApiService: CollectionApiService
    ): CollectionRemoteDataSourceImpl = CollectionRemoteDataSourceImpl(collectionApiService)

    @Provides
    @Singleton
    fun provideCurationDataSource(
        curationApiService: CurationApiService
    ): CurationRemoteDataSourceImpl = CurationRemoteDataSourceImpl(curationApiService)

    @Provides
    @Singleton
    fun provideHomeDataSource(
        homeApiService: HomeApiService
    ): HomeRemoteDataSourceImpl = HomeRemoteDataSourceImpl(homeApiService)
}