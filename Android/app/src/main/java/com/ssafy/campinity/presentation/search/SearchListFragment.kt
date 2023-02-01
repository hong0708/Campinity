package com.ssafy.campinity.presentation.search

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchListFragment : BaseFragment<FragmentSearchListBinding>(R.layout.fragment_search_list) {

    private lateinit var campsiteList: List<CampsiteBriefInfo>

    override fun initView() {
        campsiteList = listOf()
        initCampsiteList()
    }

    private fun initCampsiteList() {
        binding.rvCampsiteList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = SearchListAdapter(campsiteList) { campsiteId: String ->
                navigationToCampsiteDetailFragment(campsiteId)
            }
        }
    }

    private fun navigationToCampsiteDetailFragment(campsiteId: String) {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToCampsiteDetailFragment(
                campsiteId
            )
        )
    }
}