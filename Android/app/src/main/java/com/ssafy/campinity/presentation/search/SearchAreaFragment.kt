package com.ssafy.campinity.presentation.search

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.GridItemDecoration
import com.ssafy.campinity.common.util.dp
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterClusteringRequest
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.databinding.FragmentSearchAreaBinding
import com.ssafy.campinity.domain.entity.search.GugunItem
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.min

@AndroidEntryPoint
class SearchAreaFragment : BaseFragment<FragmentSearchAreaBinding>(R.layout.fragment_search_area) {

    private lateinit var searchAreaSiDoAdapter: SearchAreaSiDoAdapter
    private lateinit var searchAreaGuGunAdapter: SearchAreaGuGunAdapter
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private var isAllSelected = false

    override fun initView() {
        binding.viewModel = searchViewModel

        initRecyclerview()
        initListener()
        observeState()
    }

    private fun initRecyclerview() {
        initSiDo()
        initGuGun()
        binding.tvCampsiteCount.text = requireContext().getString(
            R.string.content_campsite_count,
            searchViewModel.areaList[searchAreaSiDoAdapter.selectedPosition].campsiteCountAll
        )
    }

    private fun initSiDo() {
        binding.rvSido.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )

            searchAreaSiDoAdapter = SearchAreaSiDoAdapter(
                searchViewModel, searchViewModel.areaList.toList()
            )
            adapter = searchAreaSiDoAdapter
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
                requireContext(),
                searchViewModel.areaList[0].gugunList,
                searchViewModel.selectedGugun.split(" "),
                btnWidth,
                this@SearchAreaFragment::toggleBtnSelectedAll
            )

            adapter = searchAreaGuGunAdapter

            addItemDecoration(
                GridItemDecoration(
                    context, span, 6, offsetHorizontal.dp(requireContext())
                )
            )
        }
    }

    private fun initListener() {
        binding.apply {
            llSelectAll.setOnClickListener {
                if (isAllSelected) searchAreaGuGunAdapter.unselectAll()
                else searchAreaGuGunAdapter.selectAll()

                toggleBtnSelectedAll(!isAllSelected)
            }

            llReset.setOnClickListener {
                searchViewModel.selectedGugun = ""
                searchAreaGuGunAdapter.unselectAll()
                toggleBtnSelectedAll(false)
            }

            btnSubmit.setOnClickListener {
                searchViewModel.apply {
                    selectedSido =
                        searchViewModel.areaList[searchAreaSiDoAdapter.selectedPosition].sidoName
                    selectedGugun =
                        mapGugun(searchAreaGuGunAdapter.selectedItems.toList())

                    if (selectedGugun == "") {
                        getCampsitesByFiltering(
                            SearchFilterRequest(
                                paging = 1
                            )
                        )
                        searchViewModel.getCampsitesSiGunGu(
                            SearchFilterClusteringRequest(
                                doName = sido,
                                sigunguName = gugun
                            )
                        )
                        searchViewModel.getCampsitesDo(
                            SearchFilterClusteringRequest(
                                doName = sido,
                                sigunguName = gugun
                            )
                        )
                    } else {
                        getCampsitesByFiltering(
                            SearchFilterRequest(
                                doName = selectedSido,
                                sigunguName = selectedGugun,
                                paging = 1
                            )
                        )
                        searchViewModel.getCampsitesSiGunGu(
                            SearchFilterClusteringRequest(
                                doName = sido,
                                sigunguName = gugun
                            )
                        )
                        searchViewModel.getCampsitesDo(
                            SearchFilterClusteringRequest(
                                doName = sido,
                                sigunguName = gugun
                            )
                        )
                    }
                    setStateBehaviorArea(false)
                    searchAreaGuGunAdapter.setSelectedGugun(selectedGugun.split(" "))
                }
            }
        }
    }

    private fun observeState() {
        searchViewModel.sido.observe(viewLifecycleOwner) {
            val list: List<GugunItem> =
                searchViewModel.areaList[searchAreaSiDoAdapter.selectedPosition].gugunList

            searchAreaGuGunAdapter.unselectAll()
            searchAreaGuGunAdapter.setGugun(list)

            binding.tvCampsiteCount.text = requireContext().getString(
                R.string.content_campsite_count,
                searchViewModel.areaList[searchAreaSiDoAdapter.selectedPosition].campsiteCountAll
            )
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
}