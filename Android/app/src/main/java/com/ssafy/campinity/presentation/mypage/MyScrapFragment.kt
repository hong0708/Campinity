package com.ssafy.campinity.presentation.mypage

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentMyScrapBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.home.HomeFragmentDirections
import com.ssafy.campinity.presentation.search.SearchViewModel
import kotlinx.coroutines.launch

class MyScrapFragment : BaseFragment<FragmentMyScrapBinding>(R.layout.fragment_my_scrap) {

    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private val myScrapCurationAdapter by lazy {
        MyScrapCurationAdapter(this::getCurationDetail)
    }
    private val myScrapCampingSiteAdapter by lazy {
        MyScrapCampsiteAdapter(this::getCampsite)
    }

    override fun initView() {
        initSpinner()
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            R.layout.spinner_txt
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0?.getItemAtPosition(p2).toString() == "캠핑장") {
                        myPageViewModel.etcNotesListData.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                if (response.isEmpty()) {
                                    binding.rvMyScrap.visibility = View.GONE
                                    binding.clEmptyScrap.visibility = View.VISIBLE
                                } else {
                                    binding.clEmptyScrap.visibility = View.GONE
                                    // 캠핑장 어댑터 붙이기
                                }
                            }
                        }
                    } else {
                        myPageViewModel.reviewNotesListData.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                if (response.isEmpty()) {
                                    binding.rvMyScrap.visibility = View.GONE
                                    binding.clEmptyScrap.visibility = View.VISIBLE
                                } else {
                                    binding.clEmptyScrap.visibility = View.GONE
                                    // 큐레이션 어댑터 붙이기
                                }
                            }
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }
    }

    private fun getCurationDetail(curationId: String) {
        navigate(
            HomeFragmentDirections.actionHomeFragmentToCurationDetailFragment(curationId)
        )
    }

    private fun getCampsite(campsiteId: String) {
        lifecycleScope.launch {
            val sync = searchViewModel.getCampsiteDetailAsync(campsiteId)
            navigate(HomeFragmentDirections.actionHomeFragmentToCampsiteDetailFragment(sync, -1))
        }
    }
}