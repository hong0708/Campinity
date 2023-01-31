package com.ssafy.campinity.domain.usecase.note

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.NoteDetail
import com.ssafy.campinity.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteQuestionDetailUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(questionId: String): Resource<NoteDetail> =
        withContext(Dispatchers.IO) {
            noteRepository.getQuestionsDetail(questionId)
        }
}