package com.ssafy.campinity.presentation.search

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfoPaging
import com.ssafy.campinity.presentation.base.BaseFragment
import kotlinx.coroutines.launch

class SearchListFragment : BaseFragment<FragmentSearchListBinding>(R.layout.fragment_search_list) {

    private lateinit var campsiteList: List<CampsiteBriefInfoPaging>
    private lateinit var searchListAdapter: SearchListAdapter
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        campsiteList = searchViewModel.campsiteListData.value ?: listOf()

        if (campsiteList.isEmpty())
            binding.tvCampsiteNotFound.setText(R.string.content_campsite_not_found)
        else
            binding.tvCampsiteNotFound.text = ""

        binding.piIndicator.apply {
            setStartPage(1)
            setTotalPage(5)
        }

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

    private fun navigationToCampsiteDetailFragment(position: Int, async: Int) {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToCampsiteDetailFragment(
                async,
                position
            )
        )
    }

    private fun navigationToSearchPostboxFragment(campsiteId: String) {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment(
                campsiteId
            )
        )
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

    private fun onCampsiteClickListener(position: Int, campsiteId: String) = lifecycleScope.launch {
        val async = searchViewModel.getCampsiteDetailAsync(campsiteId)
        navigationToCampsiteDetailFragment(position, async)
    }

    private suspend fun scrapCampsite(position: Int, campsiteId: String): String =
        searchViewModel.scrapCampsite(position, campsiteId)

    fun notifyItemChanged() {
        searchListAdapter.notifyDataSetChanged()
    }
}