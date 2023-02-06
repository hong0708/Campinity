package com.ssafy.campinity.di

import com.ssafy.campinity.domain.repository.*
import com.ssafy.campinity.domain.usecase.auth.GetNewTokenUseCase
import com.ssafy.campinity.domain.usecase.auth.LoginUseCase
import com.ssafy.campinity.domain.usecase.collection.GetCollectionDetailUseCase
import com.ssafy.campinity.domain.usecase.collection.GetCollectionsUseCase
import com.ssafy.campinity.domain.usecase.community.CreateCampsiteMessageUseCase
import com.ssafy.campinity.domain.usecase.community.GetCampsiteBriefInfoByCampNameUseCase
import com.ssafy.campinity.domain.usecase.community.GetCampsiteBriefInfoByUserLocationUseCase
import com.ssafy.campinity.domain.usecase.community.GetCampsiteMessageBriefInfoByScopeUseCase
import com.ssafy.campinity.domain.usecase.curation.GetCurationDetailUseCase
import com.ssafy.campinity.domain.usecase.curation.GetCurationsUseCase
import com.ssafy.campinity.domain.usecase.home.GetHomeBannersUseCase
import com.ssafy.campinity.domain.usecase.mypage.GetNotesUseCase
import com.ssafy.campinity.domain.usecase.mypage.GetUserInfoUseCase
import com.ssafy.campinity.domain.usecase.mypage.RequestLogoutUseCase
import com.ssafy.campinity.domain.usecase.note.CreateNoteAnswerUseCase
import com.ssafy.campinity.domain.usecase.note.CreateNoteQuestionUseCase
import com.ssafy.campinity.domain.usecase.note.GetNoteQuestionDetailUseCase
import com.ssafy.campinity.domain.usecase.note.GetNoteQuestionUseCase
import com.ssafy.campinity.domain.usecase.search.GetCampsiteDetailUseCase
import com.ssafy.campinity.domain.usecase.search.GetCampsitesByScopeUseCase
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
    fun provideGetNewTokenUseCase(authRepository: AuthRepository): GetNewTokenUseCase =
        GetNewTokenUseCase(authRepository)

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
    fun provideGetCurationDetailUseCase(curationRepository: CurationRepository): GetCurationDetailUseCase =
        GetCurationDetailUseCase(curationRepository)

    @Singleton
    @Provides
    fun provideGetCommunityCampsiteBriefInfoByCampNameUseCase(communityRepository: CommunityRepository)
            : GetCampsiteBriefInfoByCampNameUseCase =
        GetCampsiteBriefInfoByCampNameUseCase(communityRepository)

    @Singleton
    @Provides
    fun provideGetCommunityCampsiteBriefInfoByUserLocationUseCase(communityRepository: CommunityRepository)
            : GetCampsiteBriefInfoByUserLocationUseCase =
        GetCampsiteBriefInfoByUserLocationUseCase(communityRepository)

    @Singleton
    @Provides
    fun provideGetHomeBannersUseCase(homeRepository: HomeRepository): GetHomeBannersUseCase =
        GetHomeBannersUseCase(homeRepository)

    @Singleton
    @Provides
    fun provideGetCommunityCampsiteMessageBriefInfoByScopeUseCase(communityRepository: CommunityRepository)
            : GetCampsiteMessageBriefInfoByScopeUseCase =
        GetCampsiteMessageBriefInfoByScopeUseCase(communityRepository)

    @Singleton
    @Provides
    fun provideCreateCommunityCampsiteMessageUseCase(communityRepository: CommunityRepository)
            : CreateCampsiteMessageUseCase =
        CreateCampsiteMessageUseCase(communityRepository)

    @Singleton
    @Provides
    fun provideGetNotesUseCase(myPageRepository: MyPageRepository): GetNotesUseCase =
        GetNotesUseCase(myPageRepository)

    @Singleton
    @Provides
    fun provideGetUserInfoUseCase(myPageRepository: MyPageRepository): GetUserInfoUseCase =
        GetUserInfoUseCase(myPageRepository)

    @Singleton
    @Provides
    fun provideRequestLogoutUseCase(myPageRepository: MyPageRepository): RequestLogoutUseCase =
        RequestLogoutUseCase(myPageRepository)

    @Singleton
    @Provides
    fun provideGetCampsitesByScopeUseCase(searchRepository: SearchRepository): GetCampsitesByScopeUseCase =
        GetCampsitesByScopeUseCase(searchRepository)

    @Singleton
    @Provides
    fun provideGetCampsiteDetailUseCase(searchRepository: SearchRepository): GetCampsiteDetailUseCase =
        GetCampsiteDetailUseCase(searchRepository)
}