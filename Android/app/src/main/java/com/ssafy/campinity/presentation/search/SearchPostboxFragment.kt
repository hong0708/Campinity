package com.ssafy.campinity.presentation.search

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.LinearItemDecoration
import com.ssafy.campinity.databinding.FragmentSearchPostboxBinding
import com.ssafy.campinity.domain.entity.search.Letter
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchPostboxFragment :
    BaseFragment<FragmentSearchPostboxBinding>(R.layout.fragment_search_postbox) {

    override fun initView() {
        binding.rvMyLetter.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.VERTICAL, false
            )
            adapter = SearchPostboxAdapter(
                listOf(
                    Letter("", "질문1"),
                    Letter("", "질문2"),
                    Letter("", "질문3"),
                    Letter("", "질문4"),
                    Letter("", "질문5"),
                    Letter("", "질문6"),
                    Letter("", "질문7"),
                    Letter("", "질문8"),
                    Letter("", "질문9"),
                    Letter("", "질문10"),
                    Letter("", "질문11"),
                )
            )

            addItemDecoration(
                LinearItemDecoration(requireContext(), RecyclerView.VERTICAL, 15)
            )
        }
    }
}