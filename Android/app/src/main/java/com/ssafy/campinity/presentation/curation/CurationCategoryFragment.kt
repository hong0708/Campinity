package com.ssafy.campinity.presentation.curation

import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCurationCategoryBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CurationCategoryFragment :
    BaseFragment<FragmentCurationCategoryBinding>(R.layout.fragment_curation_category) {

    private var categoryPosition = -1
    private val curationCategoryAdapter by lazy { CurationCategoryAdapter(this::getCuration) }

    override fun initView() {
        initData()
        initRecyclerView()
    }

    private fun initData() {
        categoryPosition = arguments?.getInt("category_position")!!
    }

    private fun initRecyclerView() {
        binding.rvCuration.apply {
            adapter = curationCategoryAdapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun getCuration(curationId: String) {
        navigate(
            CurationFragmentDirections.actionCurationFragmentToCurationDetailFragment(
                curationId
            )
        )
    }
}