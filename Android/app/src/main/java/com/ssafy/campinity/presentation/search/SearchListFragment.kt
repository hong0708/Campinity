package com.ssafy.campinity.presentation.search

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.databinding.FragmentSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfoPaging
import com.ssafy.campinity.presentation.base.BaseFragment
import kotlinx.coroutines.launch

class SearchListFragment : BaseFragment<FragmentSearchListBinding>(R.layout.fragment_search_list) {

    private lateinit var campsiteList: List<CampsiteBriefInfo>
    private lateinit var searchListAdapter: SearchListAdapter
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        campsiteList = searchViewModel.campsiteListData.value?.data ?: listOf()

        initFragment()
        initCampsiteList()
        observeCampsiteListData()
    }

    private fun initFragment() {
        binding.apply {
            if (campsiteList.isEmpty())
                tvCampsiteNotFound.setText(R.string.content_campsite_not_found)
            else
                tvCampsiteNotFound.text = ""

            piIndicator.apply {
                initPageIndicator(1, 1)
                setGetNextPage { pageNum: Int -> getNextPage(pageNum) }
            }

            if (searchViewModel.campsiteListData.value != null)
                initPageIndicator(searchViewModel.campsiteListData.value!!)
        }
    }

    private fun initCampsiteList() {
        binding.rvCampsiteList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            searchListAdapter = SearchListAdapter(
                requireContext(),
                campsiteList,
                this@SearchListFragment::onCampsiteClickListener,
                this@SearchListFragment::navigationToSearchPostboxFragment,
                this@SearchListFragment::scrapCampsite,
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
            SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment(campsiteId)
        )
    }

    private fun observeCampsiteListData() {
        searchViewModel.campsiteListData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.data.isEmpty()) {
                    binding.tvCampsiteNotFound.setText(R.string.content_campsite_not_found)
                    searchListAdapter.setData(listOf())
                    binding.piIndicator.visibility = View.GONE
                } else {
                    binding.tvCampsiteNotFound.text = ""
                    searchListAdapter.setData(it.data)

                    binding.piIndicator.visibility = View.VISIBLE
                }

                if (it.currentPage % 5 == 1)
                    initPageIndicator(it)
            }
        }

//        searchViewModel.submit.observe(viewLifecycleOwner) {
//            if (searchViewModel.campsiteData.value != null) {
//                binding.piIndicator.initPageIndicator(searchViewModel.campsiteListData.value!!.maxPage)
//            }
//        }
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

    private fun initPageIndicator(campsite: CampsiteBriefInfoPaging) {
        binding.piIndicator.visibility =
            if (campsite.maxPage < 2) View.GONE
            else {
                binding.piIndicator.initPageIndicator(campsite.currentPage, campsite.maxPage)
                View.VISIBLE
            }
    }

    private fun getNextPage(pageNum: Int) {
        if (searchViewModel.selectedGugun == "")
            searchViewModel.getCampsitesByFiltering(SearchFilterRequest(paging = pageNum))
        else
            searchViewModel.getCampsitesByFiltering(
                SearchFilterRequest(
                    doName = searchViewModel.selectedSido,
                    sigunguName = searchViewModel.selectedGugun,
                    paging = pageNum
                )
            )
    }
}