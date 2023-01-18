package com.ssafy.campinity.presentation.search

import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchListBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchListFragment : BaseFragment<FragmentSearchListBinding>(R.layout.fragment_search_list) {
    override fun initView() {
        binding.rvCampsiteList.layoutManager = LinearLayoutManager(context)
    }
}