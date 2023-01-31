package com.ssafy.campinity.presentation.community.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest
import com.ssafy.campinity.domain.entity.community.NoteDetail
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.domain.usecase.note.NoteMyQuestionUseCase
import com.ssafy.campinity.domain.usecase.note.NotePostQuestionUseCase
import com.ssafy.campinity.domain.usecase.note.NoteQuestionDetailUseCase
import com.ssafy.campinity.domain.usecase.note.NoteQuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityNoteViewModel @Inject constructor(
    private val noteMyQuestionUseCase: NoteMyQuestionUseCase,
    private val noteQuestionUseCase: NoteQuestionUseCase,
    private val notePostQuestionUseCase: NotePostQuestionUseCase,
    private val noteQuestionDetailUseCase: NoteQuestionDetailUseCase
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

    fun requestNotePostQuestion(body: NoteQuestionRequest) = viewModelScope.launch {
        when (val value = notePostQuestionUseCase(body.campsiteId, body.content)) {
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
}