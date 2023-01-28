package com.ssafy.campinity.presentation.community

import androidx.lifecycle.ViewModel
import com.ssafy.campinity.domain.usecase.note.NoteMyQuestionUseCase
import javax.inject.Inject

class CommunityNoteViewModel @Inject constructor(
    private val noteMyQuestionUseCase: NoteMyQuestionUseCase
) : ViewModel() {

}