package com.ssafy.campinity.presentation.search

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.GridItemDecoration
import com.ssafy.campinity.common.util.dp
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.FragmentSearchAreaBinding
import com.ssafy.campinity.domain.entity.search.AreaListItem
import com.ssafy.campinity.domain.entity.search.GugunItem
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.min

@AndroidEntryPoint
class SearchAreaFragment : BaseFragment<FragmentSearchAreaBinding>(R.layout.fragment_search_area) {

    private lateinit var searchAreaSiDoAdapter: SearchAreaSiDoAdapter
    private lateinit var searchAreaGuGunAdapter: SearchAreaGuGunAdapter
    private val searchViewModel by viewModels<SearchViewModel>()
    private var isAllSelected = false

    override fun initView() {
        binding.viewModel = searchViewModel

        initAreaList()
        initSiDo()
        initGuGun()
        initListener()
        observeState()
    }

    override fun onResume() {
        super.onResume()
//        initListener()
    }

    private fun initAreaList() {
        searchViewModel.getSidoAll()
        Log.d("initAreaList", "initAreaList: ${searchViewModel.sidoList.value}")

        searchViewModel.sidoList.value?.forEach { sidoItem ->
            Log.d("getGugun", "sidoItem: $sidoItem")
            val sidoName = sidoItem.sido
            val campsiteCountAll = sidoItem.campsiteCountAll

            searchViewModel.getGugun(sidoName)
            searchViewModel.makeAreaList(
                AreaListItem(
                    sidoName,
                    searchViewModel.gugunList.value!!,
                    campsiteCountAll
                )
            )
        }
    }

    private fun initSiDo() {
        binding.rvSido.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )

            searchViewModel.extractSidoName()
            Log.d("initSiDo", "initSiDo: ${searchViewModel.sidoNameList.value}")
//            searchAreaSiDoAdapter = SearchAreaSiDoAdapter(
//                searchViewModel, searchViewModel.sidoNameList.value!!.toList()
//            )
//            adapter = searchAreaSiDoAdapter
        }
    }

    private fun initGuGun() {
        binding.tvCampsiteCount.text =
            requireContext().getString(R.string.content_campsite_count, 0)

        binding.rvGugun.apply {
            val layoutWidth = getDeviceWidthPx(requireContext()).dp(requireContext()) - 131
            val span = layoutWidth / 110
            val offsetHorizontal = min((layoutWidth - 110 * span) / (span - 1), 10)
            val btnWidth = (layoutWidth - offsetHorizontal * (span - 1)) / span

            layoutManager = GridLayoutManager(
                context, span, GridLayoutManager.VERTICAL, false
            )

            searchAreaGuGunAdapter = SearchAreaGuGunAdapter(
                requireContext(), listOf(), btnWidth
            ) { method: String, flag: Boolean -> toggleBtn(method, flag) }

            adapter = searchAreaGuGunAdapter

            addItemDecoration(
                GridItemDecoration(
                    context, span, 6, offsetHorizontal.dp(requireContext())
                )
            )
        }
    }

    private fun initListener() {
        binding.llSelectAll.setOnClickListener {
            if (isAllSelected) searchAreaGuGunAdapter.unselectAll()
            else searchAreaGuGunAdapter.selectAll()

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
            val gugun = searchViewModel.mapGugun(searchAreaGuGunAdapter.selectedItems.toList())

            searchViewModel.getCampsitesByArea(sido, gugun)
        }
    }

    private fun observeState() {
        searchViewModel.sido.observe(viewLifecycleOwner) {
            val list: List<GugunItem> = listOf()

            searchAreaGuGunAdapter.setData(list)
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