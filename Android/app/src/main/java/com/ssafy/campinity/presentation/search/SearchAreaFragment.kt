package com.ssafy.campinity.presentation.search

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.RecyclerviewItemDecoration
import com.ssafy.campinity.common.util.dp
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.FragmentSearchAreaBinding
import com.ssafy.campinity.domain.entity.search.AreaGugun
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAreaFragment : BaseFragment<FragmentSearchAreaBinding>(R.layout.fragment_search_area) {

    private lateinit var searchAreaSiDoAdapter: SearchAreaSiDoAdapter
    private lateinit var searchAreaGuGunAdapter: SearchAreaGuGunAdapter
    private val searchViewModel by viewModels<SearchViewModel>()
    private var isAllSelected = false

    override fun initView() {
        binding.viewModel = searchViewModel
        initSiDo()
        initGuGun()
        initListener()
    }

    private fun initSiDo() {
        binding.rvSido.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )

            searchAreaSiDoAdapter = SearchAreaSiDoAdapter(
                listOf(
                    "경기", "인천", "강원도", "대전", "세종", "충북", "충남", "울산", "경북", "경남", "전북", "전남"
                )
            )
            adapter = searchAreaSiDoAdapter
        }
    }

    private fun initGuGun() {
        binding.tvCampsiteCount.text =
            requireContext().getString(R.string.content_campsite_count, 0)

        binding.rvGugun.apply {
            layoutManager = GridLayoutManager(
                context,
                (getDeviceWidthPx(requireContext()).dp(requireContext()) - 131) / 110,
                GridLayoutManager.VERTICAL,
                false
            )

            searchAreaGuGunAdapter = SearchAreaGuGunAdapter(
                requireContext(), listOf(
                    AreaGugun("강릉시", 16),
                    AreaGugun("고성군", 16),
                    AreaGugun("동해시", 16),
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
            ) { method: String, flag: Boolean -> toggleBtn(method, flag) }

            adapter = searchAreaGuGunAdapter

            addItemDecoration(
                RecyclerviewItemDecoration(context, RecyclerView.VERTICAL, 6)
            )
        }
    }

    private fun initListener() {
        binding.llSelectAll.setOnClickListener {
            if (isAllSelected)
                searchAreaGuGunAdapter.unselectAll()
            else
                searchAreaGuGunAdapter.selectAll()

            toggleBtn("selectAll", !isAllSelected)
            toggleBtn("submit", isAllSelected)
        }

        binding.llReset.setOnClickListener {
            searchAreaGuGunAdapter.unselectAll()
            toggleBtn("selectAll", false)
            toggleBtn("submit", false)
        }

        binding.btnSubmit.setOnClickListener {
            val sido = searchAreaSiDoAdapter.selectedItem
            val gugun = searchViewModel.setGugun(searchAreaGuGunAdapter.selectedItems.toList())

            searchViewModel.getCampsitesByArea(sido, gugun)
        }
    }

    private fun toggleBtn(method: String, flag: Boolean) {
        when (method) {
            "selectAll" -> toggleBtnSelectedAll(flag)
            "submit" -> toggleBtnSubmit(flag)
        }
    }

    private fun toggleBtnSelectedAll(isAllSelected: Boolean) {
        if (isAllSelected) {
            binding.llSelectAll.setBackgroundResource(
                R.drawable.bg_rect_primrose_grey_alpha30_radius5_stroke1
            )
            this.isAllSelected = true
        } else {
            binding.llSelectAll.setBackgroundResource(
                R.drawable.bg_rect_white_grey_alpha30_radius5_stroke1
            )
            this.isAllSelected = false
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
}