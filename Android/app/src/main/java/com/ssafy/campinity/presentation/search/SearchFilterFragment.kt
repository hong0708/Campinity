package com.ssafy.campinity.presentation.search

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchFilterBinding
import com.ssafy.campinity.domain.entity.search.FilterCategory
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchFilterFragment :
    BaseFragment<FragmentSearchFilterBinding>(R.layout.fragment_search_filter) {

    private var filters = listOf<FilterCategory>()
    private lateinit var searchFilterCategoryAdapter: SearchFilterCategoryAdapter

    override fun initView() {
        createDummy()

        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.rvFilter.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )

            searchFilterCategoryAdapter = SearchFilterCategoryAdapter(
                filters, this@SearchFilterFragment::toggleBtnSubmit
            )
            adapter = searchFilterCategoryAdapter

            addItemDecoration(DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL
            ).apply {
                setDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.bg_rect_transparent_height30
                    )!!
                )
            })
        }
    }

    private fun toggleBtnSubmit(isSelected: Boolean) {
        binding.btnSubmit.apply {
            if (isSelected) {
                this.setBackgroundResource(R.drawable.bg_rect_bilbao_radius10)
                this.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                this.setBackgroundResource(R.drawable.bg_rect_white_smoke_radius10)
                this.setTextColor(ContextCompat.getColor(requireContext(), R.color.zambezi))
            }
        }
    }

    private fun createDummy() {
        filters = listOf(
            FilterCategory(
                "내부 시설", listOf("난방기구", "유무선인터넷", "내부화장실", "TV", "난방기구", "유무선인터넷", "내부화장실", "TV")
            ), FilterCategory(
                "내부 시설", listOf("난방기구", "유무선인터넷")
            ), FilterCategory(
                "내부 시설", listOf("난방기구", "유무선인터넷", "내부화장실", "TV")
            ), FilterCategory(
                "내부 시설", listOf("난방기구", "유무선인터넷", "내부화장실", "TV", "난방기구", "유무선인터넷", "내부화장실", "TV")
            ), FilterCategory(
                "내부 시설", listOf("난방기구")
            ), FilterCategory(
                "내부 시설", listOf("난방기구")
            ), FilterCategory(
                "내부 시설", listOf("난방기구", "유무선인터넷", "내부화장실")
            )
        )
    }
}