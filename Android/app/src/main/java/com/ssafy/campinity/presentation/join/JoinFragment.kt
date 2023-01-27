package com.ssafy.campinity.presentation.join

import androidx.fragment.app.viewModels
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentJoinBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val viewModel by viewModels<JoinViewModel>()

    override fun initView() {

    }
}