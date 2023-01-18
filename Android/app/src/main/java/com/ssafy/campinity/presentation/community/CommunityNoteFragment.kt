package com.ssafy.campinity.presentation.community

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityNoteFragment :
    BaseFragment<FragmentCommunityNoteBinding>(R.layout.fragment_community_note) {

    override fun initView() {
        initListener()
    }

    private fun initListener() {

    }
}