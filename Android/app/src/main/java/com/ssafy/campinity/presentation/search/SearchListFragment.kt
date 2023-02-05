package com.ssafy.campinity.presentation.search

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchListFragment : BaseFragment<FragmentSearchListBinding>(R.layout.fragment_search_list) {

    private lateinit var campsiteList: List<CampsiteBriefInfo>
    private lateinit var searchListAdapter: SearchListAdapter
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        campsiteList = searchViewModel.campsiteListData.value ?: listOf()

        if (campsiteList.isEmpty())
            binding.tvCampsiteNotFound.visibility = View.VISIBLE
        else
            binding.tvCampsiteNotFound.visibility = View.GONE

        initCampsiteList()
        observeCampsiteListData()
    }

    private fun initCampsiteList() {
        binding.rvCampsiteList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            searchListAdapter = SearchListAdapter(
                campsiteList,
                this@SearchListFragment::navigationToCampsiteDetailFragment,
                this@SearchListFragment::navigationToSearchPostboxFragment,
                this@SearchListFragment::campsiteItemClickListener
            )
            adapter = searchListAdapter
        }
    }

    private fun navigationToCampsiteDetailFragment() {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToCampsiteDetailFragment()
        )
    }

    private fun navigationToSearchPostboxFragment() {
        navigate(SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment())
    }

    private fun observeCampsiteListData() {
        searchViewModel.campsiteListData.observe(viewLifecycleOwner) {
            if (it == null || it.isEmpty())
                binding.tvCampsiteNotFound.setText(R.string.content_campsite_not_found)
            else
                binding.tvCampsiteNotFound.text = ""

            searchListAdapter.setData(it ?: listOf())
        }
    }

    private fun campsiteItemClickListener(campsiteId: String) {
        searchViewModel.getCampsiteDetail(campsiteId)
    }
}