package com.ssafy.campinity.domain.usecase.note

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.auth.AuthRequest
import com.ssafy.campinity.domain.entity.auth.Token
import com.ssafy.campinity.domain.repository.AuthRepository
import com.ssafy.campinity.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteMyQuestionUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    /*suspend operator fun invoke(body: AuthRequest): Resource<Token> = withContext(Dispatchers.IO) {
        authRepository.loginRequest(body)
    }*/
}