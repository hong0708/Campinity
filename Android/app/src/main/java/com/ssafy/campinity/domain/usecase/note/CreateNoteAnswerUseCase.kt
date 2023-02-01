package com.ssafy.campinity.domain.usecase.note

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionAnswerRequest
import com.ssafy.campinity.domain.entity.community.NoteQuestionAnswer
import com.ssafy.campinity.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateNoteAnswerUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        noteQuestionAnswerRequest: NoteQuestionAnswerRequest
    ): Resource<NoteQuestionAnswer> =
        withContext(Dispatchers.IO) {
            noteRepository.createAnswer(noteQuestionAnswerRequest)
        }
}