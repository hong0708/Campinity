package com.ssafy.campinity.presentation.community.note

import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest

interface CommunityNoteQuestionDialogInterface {
    fun postNoteQuestion(body: NoteQuestionRequest)
}