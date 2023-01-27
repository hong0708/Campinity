package com.ssafy.campinity.presentation.join

import androidx.lifecycle.ViewModel
import com.ssafy.campinity.domain.usecase.user.EditUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val editUserUseCase: EditUserUseCase
) : ViewModel() {

}