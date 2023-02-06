package com.ssafy.campinity.presentation.curation

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.GridItemDecoration
import com.ssafy.campinity.databinding.FragmentCurationCategoryBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CurationCategoryFragment :
    BaseFragment<FragmentCurationCategoryBinding>(R.layout.fragment_curation_category) {

    private val curationCategoryAdapter by lazy { CurationCategoryAdapter(this::getCuration) }
    private val curationViewModel by activityViewModels<CurationViewModel>()

    override fun initView() {
        initRecyclerView()
    }

    private fun initData() {
        curationViewModel.categoryState.observe(viewLifecycleOwner) {
            when (it) {
                "전체" -> curationViewModel.getCurations("")
                "튜토리얼" -> curationViewModel.getCurations("튜토리얼")
                "레시피" -> curationViewModel.getCurations("레시피")
                "장소 추천" -> curationViewModel.getCurations("장소추천")
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvCuration.apply {
            adapter = curationCategoryAdapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(GridItemDecoration(context, 2, 4, 0))
        }
        curationViewModel.curationListData.observe(viewLifecycleOwner) {
            it?.let { curationCategoryAdapter.setCuration(it) }
        }
        initData()
    }

    private fun getCuration(curationId: String) {
        navigate(
            CurationFragmentDirections.actionCurationFragmentToCurationDetailFragment(
                curationId
            )
        )
    }
}