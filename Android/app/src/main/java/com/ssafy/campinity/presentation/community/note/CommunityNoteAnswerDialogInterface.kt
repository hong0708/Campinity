package com.ssafy.campinity.presentation.community.note


interface CommunityNoteAnswerDialogInterface {
    fun postNoteAnswer(answerContent: String, questionId: String)
}