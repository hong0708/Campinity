package com.ssafy.campinity.domain.usecase.note

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteQuestionUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(campsiteId: String): Resource<List<NoteQuestionTitle>> =
        withContext(Dispatchers.IO) {
            noteRepository.getQuestions(campsiteId)
        }
}