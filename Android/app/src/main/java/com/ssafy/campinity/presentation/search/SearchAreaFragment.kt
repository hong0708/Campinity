package com.ssafy.campinity.presentation.search

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.dp
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.FragmentSearchAreaBinding
import com.ssafy.campinity.domain.entity.search.AreaGugun
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchAreaFragment : BaseFragment<FragmentSearchAreaBinding>(R.layout.fragment_search_area) {
    private lateinit var searchAreaGuGunAdapter: SearchAreaGuGunAdapter

    override fun initView() {
        initSido()
        initGugun()
        initListener()
    }

    private fun initSido() {
        binding.rvSido.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = SearchAreaSidoAdapter(
                listOf(
                    "경기", "인천", "강원", "대전", "세종", "충북", "충남", "울산", "경북", "경남", "전북", "전남"
                )
            )
        }
    }

    private fun initGugun() {
        binding.rvGugun.apply {
            layoutManager = GridLayoutManager(
                context,
                (getDeviceWidthPx(requireContext()).dp(requireContext()) - 131) / 110,
                GridLayoutManager.VERTICAL,
                false
            )
            searchAreaGuGunAdapter = SearchAreaGuGunAdapter(
                listOf(
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16),
                    AreaGugun("가평군", 16)
                )
            )
            adapter = searchAreaGuGunAdapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    .apply {
                        setDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_rect_transparent_height6
                            )!!
                        )
                    }
            )
        }
    }

    private fun initListener() {
        binding.llSelectAll.setOnClickListener {
            searchAreaGuGunAdapter.selectAll()
            // submit button toggle
        }

        binding.llReset.setOnClickListener {
            searchAreaGuGunAdapter.unselectAll()
            // Don't submit button toggle
        }

    }
}