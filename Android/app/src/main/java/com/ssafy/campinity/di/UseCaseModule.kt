package com.ssafy.campinity.di

import com.ssafy.campinity.domain.repository.AuthRepository
<<<<<<< Android/app/src/main/java/com/ssafy/campinity/di/UseCaseModule.kt
import com.ssafy.campinity.domain.repository.CollectionRepository
import com.ssafy.campinity.domain.repository.CurationRepository
import com.ssafy.campinity.domain.repository.NoteRepository
import com.ssafy.campinity.domain.repository.UserRepository
import com.ssafy.campinity.domain.usecase.auth.LoginUseCase
import com.ssafy.campinity.domain.repository.CollectionRepository
import com.ssafy.campinity.domain.usecase.collection.GetCollectionDetailUseCase
import com.ssafy.campinity.domain.usecase.collection.GetCollectionsUseCase
import com.ssafy.campinity.domain.usecase.curation.GetCurationDetailUseCase
import com.ssafy.campinity.domain.usecase.curation.GetCurationsUseCase
import com.ssafy.campinity.domain.usecase.note.CreateNoteAnswerUseCase
import com.ssafy.campinity.domain.usecase.note.CreateNoteQuestionUseCase
import com.ssafy.campinity.domain.usecase.note.GetNoteQuestionDetailUseCase
import com.ssafy.campinity.domain.usecase.note.GetNoteQuestionUseCase
import com.ssafy.campinity.domain.usecase.user.CheckDuplicationUseCase
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
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(authRepository)

    @Singleton
    @Provides
    fun provideEditUserUseCase(userRepository: UserRepository): EditUserUseCase =
        EditUserUseCase(userRepository)

    @Singleton
    @Provides
    fun provideCheckDuplicationUseCase(userRepository: UserRepository): CheckDuplicationUseCase =
        CheckDuplicationUseCase(userRepository)

    @Singleton
    @Provides
    fun provideGetNoteQuestionUseCase(noteRepository: NoteRepository): GetNoteQuestionUseCase =
        GetNoteQuestionUseCase(noteRepository)

    @Singleton
    @Provides
    fun providePostNoteQuestionUseCase(noteRepository: NoteRepository): CreateNoteQuestionUseCase =
        CreateNoteQuestionUseCase(noteRepository)

    @Singleton
    @Provides
    fun provideGetNoteQuestionDetailUseCase(noteRepository: NoteRepository): GetNoteQuestionDetailUseCase =
        GetNoteQuestionDetailUseCase(noteRepository)

    @Singleton
    @Provides
    fun providePostNoteAnswerUseCase(noteRepository: NoteRepository): CreateNoteAnswerUseCase =
        CreateNoteAnswerUseCase(noteRepository)

    @Singleton
    @Provides
    fun provideGetCollectionsUseCase(collectionRepository: CollectionRepository): GetCollectionsUseCase =
        GetCollectionsUseCase(collectionRepository)

    @Singleton
    @Provides
    fun provideGetCollectionDetailUseCase(collectionRepository: CollectionRepository): GetCollectionDetailUseCase =
        GetCollectionDetailUseCase(collectionRepository)

    @Singleton
    @Provides
    fun provideGetCurationsUseCase(curationRepository: CurationRepository): GetCurationsUseCase =
        GetCurationsUseCase(curationRepository)

    @Singleton
    @Provides
    fun provideGetCurationUseCase(curationRepository: CurationRepository): GetCurationDetailUseCase =
        GetCurationDetailUseCase(curationRepository)
}