package com.ssafy.campinity.presentation.community

import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest

interface CommunityNoteAnswerDialogInterface {
    fun postNoteQuestion(body: NoteQuestionRequest)
}