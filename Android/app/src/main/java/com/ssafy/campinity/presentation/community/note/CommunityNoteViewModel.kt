package com.ssafy.campinity.presentation.community.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionAnswerRequest
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest
import com.ssafy.campinity.domain.entity.community.NoteDetail
import com.ssafy.campinity.domain.entity.community.NoteQuestionAnswer
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.domain.usecase.note.CreateNoteAnswerUseCase
import com.ssafy.campinity.domain.usecase.note.CreateNoteQuestionUseCase
import com.ssafy.campinity.domain.usecase.note.GetNoteQuestionDetailUseCase
import com.ssafy.campinity.domain.usecase.note.GetNoteQuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommunityNoteViewModel @Inject constructor(
    private val getNoteQuestionUseCase: GetNoteQuestionUseCase,
    private val createNoteQuestionUseCase: CreateNoteQuestionUseCase,
    private val noteQuestionDetailUseCase: GetNoteQuestionDetailUseCase,
    private val createNoteAnswerUseCase: CreateNoteAnswerUseCase
) : ViewModel() {

    private val _noteQuestions = MutableLiveData<List<NoteQuestionTitle>>()
    val noteQuestions: LiveData<List<NoteQuestionTitle>> = _noteQuestions

    private val _noteMyQuestions = MutableLiveData<List<NoteQuestionTitle>>()
    val noteMyQuestions: LiveData<List<NoteQuestionTitle>> = _noteMyQuestions

    private val _noteQuestionDetail = MutableLiveData<NoteDetail>()
    val noteQuestionDetail: LiveData<NoteDetail> = _noteQuestionDetail

    fun getNoteQuestions(campsiteId: String) = viewModelScope.launch {
        when (val value = getNoteQuestionUseCase(campsiteId)) {
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

    fun getNoteMyQuestions(campsiteId: String) = viewModelScope.launch {
        when (val value = getNoteQuestionUseCase(campsiteId)) {
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

    suspend fun postNoteQuestion(campsiteId: String, content: String): Boolean {
        return withContext(viewModelScope.coroutineContext) {
            val noteQuestionRequest = NoteQuestionRequest(campsiteId, content)
            when (val value = createNoteQuestionUseCase(noteQuestionRequest)) {
                is Resource.Success<NoteQuestionTitle> -> {
                    return@withContext true
                }
                is Resource.Error -> {
                    Log.d("requestNotePostQuestion", "NotePostQuestion: ${value.errorMessage}")
                    return@withContext false
                }
            }
        }
    }

    fun getNoteQuestionDetail(questionId: String) = viewModelScope.launch {
        when (val value = noteQuestionDetailUseCase(questionId)) {
            is Resource.Success<NoteDetail> -> {
                val noteDetail = value.data
                _noteQuestionDetail.value = noteDetail
            }
            is Resource.Error -> {
                Log.d("requestNoteQuestionDetail", "NoteQuestionDetail: ${value.errorMessage}")
            }
        }
    }

    suspend fun postNoteAnswer(answerContent: String, questionId: String,): Boolean {
        return withContext(viewModelScope.coroutineContext) {
            val noteQuestionAnswerRequest = NoteQuestionAnswerRequest(
                answerContent,
                questionId
            )
            when (val value = createNoteAnswerUseCase(noteQuestionAnswerRequest)) {
                is Resource.Success<NoteQuestionAnswer> -> {
                    return@withContext true
                }
                is Resource.Error -> {
                    Log.d("requestNotePostAnswer", "NotePostAnswer: ${value.errorMessage}")
                    return@withContext false
                }
            }
        }
    }
}