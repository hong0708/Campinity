package com.ssafy.campinity.presentation.join

import androidx.lifecycle.lifecycleScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCompleteJoinBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteJoinFragment :
    BaseFragment<FragmentCompleteJoinBinding>(R.layout.fragment_complete_join) {

    override fun initView() {
        binding.tvContentCompleteJoin.text =
            String.format(resources.getString(R.string.content_join_complete), ApplicationClass.preferences.nickname)
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            navigate(CompleteJoinFragmentDirections.actionCompleteJoinFragmentToHomeFragment())
        }
    }
}