package com.ssafy.campinity.presentation.curation

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCurationCategoryBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CurationCategoryFragment : BaseFragment<FragmentCurationCategoryBinding>(R.layout.fragment_curation_category) {

    private var categoryPosition = -1

    override fun initView() {
        categoryPosition = arguments?.getInt("category_position")!!
    }
}