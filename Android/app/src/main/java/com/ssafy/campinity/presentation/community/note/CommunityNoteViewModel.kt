package com.ssafy.campinity.presentation.community.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionAnswerRequest
import com.ssafy.campinity.domain.entity.community.NoteDetail
import com.ssafy.campinity.domain.entity.community.NoteQuestionAnswer
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.domain.usecase.note.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityNoteViewModel @Inject constructor(
    private val noteMyQuestionUseCase: NoteMyQuestionUseCase,
    private val noteQuestionUseCase: NoteQuestionUseCase,
    private val notePostQuestionUseCase: NotePostQuestionUseCase,
    private val noteQuestionDetailUseCase: NoteQuestionDetailUseCase,
    private val notePostAnswerUseCase: NotePostAnswerUseCase
) : ViewModel() {

    private val _noteQuestions = MutableLiveData<List<NoteQuestionTitle>>()
    val noteQuestions: LiveData<List<NoteQuestionTitle>> = _noteQuestions

    private val _noteMyQuestions = MutableLiveData<List<NoteQuestionTitle>>()
    val noteMyQuestions: LiveData<List<NoteQuestionTitle>> = _noteMyQuestions

    private val _noteQuestionDetail = MutableLiveData<NoteDetail>()
    val noteQuestionDetail: LiveData<NoteDetail> = _noteQuestionDetail

    fun requestNoteQuestions(campsiteId: String) = viewModelScope.launch {
        when (val value = noteQuestionUseCase(campsiteId)) {
            is Resource.Success<List<NoteQuestionTitle>> -> {
                val noteQuestionList = value.data
                _noteQuestions.value = noteQuestionList
            }
            is Resource.Error -> {
                Log.d("requestNoteQuestions", "NoteQuestions: ${value.errorMessage}")
                _noteQuestions.value = listOf()
            }
        }
    }

    fun requestNoteMyQuestions(campsiteId: String) = viewModelScope.launch {
        when (val value = noteMyQuestionUseCase(campsiteId)) {
            is Resource.Success<List<NoteQuestionTitle>> -> {
                val noteQuestionList = value.data
                _noteMyQuestions.value = noteQuestionList
            }
            is Resource.Error -> {
                Log.d("requestNoteMyQuestions", "NoteMyQuestions: ${value.errorMessage}")
                _noteMyQuestions.value = listOf()
            }
        }
    }

    fun requestNotePostQuestion(campsiteId: String, content: String) = viewModelScope.launch {
        when (val value = notePostQuestionUseCase(campsiteId, content)) {
            is Resource.Success<NoteQuestionTitle> -> {
                Log.d("requestNoteMyQuestions", "NoteMyQuestions: ${value.data}")
            }
            is Resource.Error -> {
                Log.d("requestNoteMyQuestions", "NoteMyQuestions: ${value.errorMessage}")
            }
        }
    }

    fun requestNoteQuestionDetail(questionId: String) = viewModelScope.launch {
        when (val value = noteQuestionDetailUseCase(questionId)) {
            is Resource.Success<NoteDetail> -> {
                val noteDetail = value.data
                _noteQuestionDetail.value = noteDetail
            }
            is Resource.Error -> {
                Log.d("requestNoteMyQuestions", "NoteMyQuestions: ${value.errorMessage}")
            }
        }
    }

    fun requestNotePostAnswer(answerContent: String, questionId: String) =
        viewModelScope.launch {
            val noteQuestionAnswerRequest = NoteQuestionAnswerRequest(
                answerContent,
                questionId
            )
            when (val value = notePostAnswerUseCase(noteQuestionAnswerRequest)) {
                is Resource.Success<NoteQuestionAnswer> -> {
                    Log.d("requestNotePostAnswer", "requestNotePostAnswer: ${value.data}")
                }
                is Resource.Error -> {
                    Log.d("requestNotePostAnswer", "requestNotePostAnswer: ${value.errorMessage}")
                }
            }
        }
}