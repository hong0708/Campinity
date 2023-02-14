package com.ssafy.campinity.presentation.search

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.presentation.base.BaseFragment
import kotlinx.coroutines.launch

class SearchListFragment : BaseFragment<FragmentSearchListBinding>(R.layout.fragment_search_list) {

    private lateinit var campsiteList: List<CampsiteBriefInfo>
    private lateinit var searchListAdapter: SearchListAdapter
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        campsiteList = searchViewModel.campsiteListData.value ?: listOf()

        if (campsiteList.isEmpty())
            binding.tvCampsiteNotFound.setText(R.string.content_campsite_not_found)
        else
            binding.tvCampsiteNotFound.text = ""

        initCampsiteList()
        observeCampsiteListData()
    }

    private fun initCampsiteList() {
        binding.rvCampsiteList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            searchListAdapter = SearchListAdapter(
                requireContext(),
                campsiteList,
                this@SearchListFragment::onCampsiteClickListener,
                this@SearchListFragment::navigationToSearchPostboxFragment,
                this@SearchListFragment::scrapCampsite
            )
            adapter = searchListAdapter
        }
    }

    private fun navigationToCampsiteDetailFragment(async: Int) {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToCampsiteDetailFragment(async)
        )
    }

    private fun navigationToSearchPostboxFragment(campsiteId: String) {
        navigate(SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment(campsiteId))
    }

    private fun observeCampsiteListData() {
        searchViewModel.campsiteListData.observe(viewLifecycleOwner) {
            if (it == null || it.isEmpty()) {
                binding.tvCampsiteNotFound.setText(R.string.content_campsite_not_found)
                searchListAdapter.setData(listOf())
            } else {
                binding.tvCampsiteNotFound.text = ""
                searchListAdapter.setData(it)
            }
        }
    }

    private fun onCampsiteClickListener(campsiteId: String) = lifecycleScope.launch {
        val async = searchViewModel.getCampsiteDetailAsync(campsiteId)
        navigationToCampsiteDetailFragment(async)
    }

    private suspend fun scrapCampsite(campsiteId: String): String =
        searchViewModel.scrapCampsite(campsiteId)
}