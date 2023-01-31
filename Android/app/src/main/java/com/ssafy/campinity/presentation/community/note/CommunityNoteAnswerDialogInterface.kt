package com.ssafy.campinity.presentation.community.note

import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest

interface CommunityNoteAnswerDialogInterface {
    fun postNoteQuestion(body: NoteQuestionRequest)
}