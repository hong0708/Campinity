package com.ssafy.campinity.presentation.curation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CurationAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return CurationCategoryFragment().apply {
            arguments = Bundle().apply {
                putInt("category_position", position)
            }
        }
    }
}